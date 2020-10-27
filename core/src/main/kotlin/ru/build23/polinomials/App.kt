package ru.build23.polinomials

import com.artemis.Entity
import com.artemis.InvocationStrategy
import com.artemis.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.stage
import ktx.app.clearScreen
import ktx.math.vec3
import ktx.scene2d.label
import ktx.scene2d.scene2d
import ru.build23.polinomials.Global.mode
import ru.build23.polinomials.artemis.add
import ru.build23.polinomials.artemis.entity
import ru.build23.polinomials.artemis.worldConfiguration

enum class Mode {
  Create, Edit, Delete
}

class App(
  batch: SpriteBatch
) : Disposable {

  private val world: World
  private val sceneCamera = OrthographicCamera()
  private val worldCamera = OrthographicCamera()
  val stage = stage(batch, FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), sceneCamera))
  private val shapeRenderer = ShapeRenderer()

  private val curve: Entity

  init {
    val config = worldConfiguration {
      Gdx.input.inputProcessor = stage
      with(
        ControlPointAddSystem(),
        ControlPointDeleteSystem(),
        VelocitySystem(),
        CameraSystem(),
        BezierComposerSystem(),
        GridComposerSystem(),
        GridOutlineSystem(),
        CurveRendererSystem(),
        DebugLineRendererSystem(),
        DebugCircleRendererSystem(),
        DebugRectRendererSystem(),
        StageRendererSystem()
      )
      register(InvocationStrategy())
    }.apply {
      register("scene_camera", sceneCamera)
      register("world_camera", worldCamera)
      register(shapeRenderer)
      register(stage)
    }
    world = World(config)
    world.inject(Global)

    world.entity().camera()
    curve = world.entity().bezier()

    stage.addListener(object : ClickListener() {
      override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
        super.touchUp(event, x, y, pointer, button)
        val pos = sceneCamera.unproject(vec3(x, sceneCamera.viewportHeight - y, 0f))
        when (mode) {
          Mode.Create -> {
            curve.bezierCurvePoint(pos.x, pos.y)
          }
          Mode.Delete -> {
            curve.deleteCurvePoint(pos.x, pos.y)
          }
          else -> {
          }
        }
      }
    })
  }

  fun resize(width: Int, height: Int) {
    stage.viewport.update(width, height)
  }

  fun render(delta: Float) {
    clearScreen(0f, 0f, 0f, 1f)
    shapeRenderer.projectionMatrix = sceneCamera.combined
    world.delta = delta
    world.process()
    if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
      mode = Mode.Create
    } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
      mode = Mode.Edit
    }
  }

  override fun dispose() {
    world.dispose()
    stage.dispose()
    shapeRenderer.dispose()
  }
}