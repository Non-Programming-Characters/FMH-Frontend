from abc import ABC, abstractmethod

import flet


class Template(ABC):

    def __init__(self):
        self.render_module = None

    @abstractmethod
    def load(self, page: flet.Page) -> Template: pass

    def get_render_module(self):
        return self.render_module