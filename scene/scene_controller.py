
class SceneController:

    def __init__(self):
        self.scene = None
        self.components = []

    def set_scene(self, scene) -> None:
        self.scene = scene

    def set_component(self) -> list:
        return self.components