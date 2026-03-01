import abc
from abc import ABC

import flet

class SceneView(ABC):

    def __init__(self, route: str):
        self.route = route
        self.render_module = None

    @abc.abstractmethod
    def load(self, page: flet.Page) -> SceneView: pass

    def get_render_module(self) -> flet.Control:
        return self.render_module

    def get_route(self) -> str:
        return self.route