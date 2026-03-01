import inspect
from typing import Any, Dict, Type

class DIContainer:
    _instance = None
    _registry: Dict[Type, Any] = {}

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super().__new__(cls)
        return cls._instance

    def register(self, cls: Type, instance: Any = None):
        self._registry[cls] = instance
        return cls

    def get(self, cls: Type) -> Any:
        if cls not in self._registry:
            raise ValueError(f"{cls.__name__} не зарегистрирован")

        if self._registry[cls] is None:
            self._registry[cls] = self._resolve_dependencies(cls)
        return self._registry[cls]

    def _resolve_dependencies(self, cls: Type) -> Any:
        sig = inspect.signature(cls.__init__)
        kwargs = {}

        for name, param in sig.parameters.items():
            if name == 'self':
                continue

            annotation = param.annotation

            if annotation != inspect.Parameter.empty:
                if annotation in self._registry:
                    kwargs[name] = self.get(annotation)
                elif param.default != inspect.Parameter.empty:
                    kwargs[name] = param.default
                else:
                    raise Exception(
                        f"Не удалось разрешить зависимость '{name}' ({annotation.__name__}) для класса {cls.__name__}")

        return cls(**kwargs)

container = DIContainer()

def component(cls):
    return container.register(cls)