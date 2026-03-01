from typing import List

from scene.view.view import SceneView

class SceneController:

    def __init__(self):
        self.scene = None
        self.scene_container: List[SceneView] = []

    def find_scene_by_route(self, route: str) -> SceneView | None:
        for scene in self.scene_container:
            if scene.get_route() != route: continue
            return scene

        return None

    def register_scene(self, scene: List[SceneView]):
        for scene in scene:
            self.scene_container.append(scene)

    def set_scene(self, scene) -> None:
        self.scene = scene