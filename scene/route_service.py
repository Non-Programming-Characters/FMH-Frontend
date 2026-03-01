from core.container_di import component

class RouterDefinition:

    def __init__(self, initial_route=''):
        self.route_path = initial_route

    def route(self, route: str) -> None:
        self.route_path = route


    def get_route(self) -> str:
        return self.route_path