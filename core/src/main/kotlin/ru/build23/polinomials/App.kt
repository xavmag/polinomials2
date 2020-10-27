package ru.build23.polinomials

import com.artemis.InvocationStrategy
import com.artemis.World
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.stage
import ru.build23.polinomials.Global.mNurbs
import ru.build23.polinomials.artemis.entity
import ru.build23.polinomials.artemis.get
import ru.build23.polinomials.artemis.worldConfiguration

class App(
  private val batch: SpriteBatch
) : Disposable {

  private val world: World
  private val sceneCamera = OrthographicCamera()
  private val worldCamera = OrthographicCamera()
  private val stage = stage(batch, ScreenViewport(sceneCamera))

  init {
    val config = worldConfiguration {
      with(
        ControlPointAddSystem(),
        ControlPointDeleteSystem(),
        VelocitySystem(),
        CameraSystem(),
        NurbsComposerSystem(),
        GridComposerSystem(),
        GridOutlineSystem(),
        ShapeRendererBeginSystem(),
        CurveRendererSystem(),
        DebugLineRendererSystem(),
        DebugCircleRendererSystem(),
        DebugRectRendererSystem(),
        ShapeRendererEndSystem(),
        StageRendererSystem()
      )
      register(InvocationStrategy())
    }.apply {
      register("scene_camera", sceneCamera)
      register("world_camera", worldCamera)
      register(ShapeRenderer())
      register(stage)
    }
    world = World(config)
    world.inject(Global)

    world.entity().camera()
    val curve = world.entity().nurbs(3)
    curve.controlPoint(100f, 100f)
    curve.controlPoint(200f, 200f)
    curve.controlPoint(250f, 100f)
  }

  fun resize(width: Int, height: Int) {
    stage.viewport.update(width, height)
  }

  fun render(delta: Float) {
    world.delta = delta
    world.process()
  }

  override fun dispose() {
    world.dispose()
    stage.dispose()
  }
}