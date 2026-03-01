import flet

ICON_MAP = {
    "BOY": flet.Icons.BOY
}

def get_icon_equals_ignore_case(name: str) -> flet.IconData:
    return ICON_MAP.get(name.upper())