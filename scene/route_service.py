class RouterDefinition:

    def __init__(self):
        self.route = ''

    def route(self, route):
        self.route = route

    def get_route(self):
        return self.route