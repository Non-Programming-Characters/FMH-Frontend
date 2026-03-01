import flet

from core.class_di import registry
from core.component_type import ComponentType
from core.container_di import component, container
from scene.route_service import RouterDefinition
from scene.scene_controller import SceneController
from scene.view.view import SceneView

@component
@registry.register(component_type=ComponentType.SCENE_VIEW, name="TEST_VIEW")
class TestView(SceneView):

    def __init__(self, scene_controller: SceneController, router: RouterDefinition):
        super().__init__('/test')
        self.router = router
        self.scene_controller = scene_controller

    def load(self, page: flet.Page) -> SceneView:
        navigation_control = container.get(registry.get("NAVIGATION_TEMPLATE")).load(page).get_render_module()
        content_control = flet.Container()

        content_control.content = flet.Text("Контент test страницы")

        self.render_module = flet.Column(controls=[content_control, navigation_control])
        return self