
class ClassComponentRegistry:
    def __init__(self):
        self._registry = {}

    def register(self, name=None, component_type=None):
        def decorator(cls):
            key = (name or cls.__name__, component_type)
            self._registry[key] = cls
            return cls
        return decorator

    def get_all_keys(self):
        return self._registry.copy().keys()

    def get_all_values(self):
        return self._registry.copy().values()

    def get_all_with_filter_key(self, action_filter) -> list:
        return [
            self._registry[key]
            for key in self._registry
            if action_filter(key)
        ]

    def get_by_name_and_type(self, name, component_type):
        return self._registry.get((name, component_type))

    def get(self, name, component_type=None):
        if component_type:
            return self._registry.get((name, component_type))

        for (key_name, _), cls in self._registry.items():
            if key_name == name:
                return cls
        return None

registry = ClassComponentRegistry()