import flet

from core.class_di import registry
from core.component_type import ComponentType
from core.container_di import component
from scene.route_service import RouterDefinition
from scene.scene_controller import SceneController
from scene.template.template import Template

@component
@registry.register(name="NAVIGATION_TEMPLATE", component_type=ComponentType.TEMPLATE)
class NavigationTemplate(Template):

    def __init__(self, scene_controller: SceneController, router: RouterDefinition):
        super().__init__()
        self.router = router
        self.scene_controller = scene_controller

    def load(self, page: flet.Page) -> Template:
        navigation_control = flet.Container()

        def on_home_click(e):
            page.go('/home')

        def on_library_click(e):
            page.go('/library')

        def on_test_click(e):
            page.go('/test')

        navigation_control.content = flet.Row(
            controls=[
                flet.Button(
                    content=flet.Text("Главная"),
                    icon=flet.Icons.CSS,
                    data="/home",
                    on_click=on_home_click,
                    bgcolor=flet.Colors.BLACK if self.router.get_route() == '/home' else flet.Colors.WHITE,
                ),
                flet.Button(
                    content=flet.Text("Библиотека"),
                    icon=flet.Icons.VIEW_COZY,
                    data="/library",
                    on_click=on_library_click,
                    bgcolor=flet.Colors.BLACK if self.router.get_route() == '/library' else flet.Colors.WHITE,
                ),
                flet.Button(
                    content=flet.Text("Тестирование"),
                    icon=flet.Icons.CSS,
                    data="/test",
                    on_click=on_test_click,
                    bgcolor=flet.Colors.BLACK if self.router.get_route() == '/test' else flet.Colors.WHITE,
                )
            ]
        )

        self.render_module = navigation_control

        return self