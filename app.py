import flet as ft

from core.class_di import registry
from core.container_di import container
from scene.route_service import RouterDefinition
from scene.scene_controller import SceneController

# НЕ ТРОГАТЬ ЭТО!!!! НУЖНО ЯВНО ПРОПИСЫВАТЬ ИМПОРТЫ, ЧТОБЫ РАБОТАЛ DI
from scene.template import navigation_template
from scene.template.template import Template
from scene.view.impl import library_view
from scene.view.impl import home_view
from scene.view.impl import card_view
from scene.view.impl import test_view
from scene import scene_controller
from scene import route_service

from scene.view.view import SceneView


class App:

    def __init__(self):
        self.situation = None
        self.router = RouterDefinition('/home')
        self.scene_controller = SceneController()

        container.register(RouterDefinition, self.router)
        container.register(SceneController, self.scene_controller)

        view_instances = []
        for cls in registry.get_all_values():
            if not issubclass(cls, SceneView): continue

            instance = container.get(cls)

            view_instances.append(instance)

        self.scene_controller.register_scene(view_instances)

    def base_page(self, page: ft.Page):
        page.go('/home')
        page.on_route_change = lambda e: self._on_route_change(e)

        self._show_scene(page, page.route)
        page.update()

    def _on_route_change(self, e):
        self._show_scene(e.page, e.page.route)

    def _show_scene(self, page: ft.Page, route: str):
        scene = self.scene_controller.find_scene_by_route(route)
        if scene:
            self.router.route(route)
            page.controls.clear()
            page.controls.append(scene.load(page).get_render_module())
            page.update()

app = App()

def run():
    ft.app(target=app.base_page)

def get_app() -> App:
    return app