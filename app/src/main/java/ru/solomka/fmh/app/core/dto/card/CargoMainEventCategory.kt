package ru.solomka.fmh.app.core.dto.card

enum class CargoMainEventCategory(val title: String) {
    ACCIDENT("ДТП"),

    ROAD_HAZARD("Опасность на дороге"),

    INFRASTRUCTURE("Инфраструктура");

    enum class SubEventCategory(val title: String) {
        COLLISION_VEHICLE("Cтолкновение ТС"),
        COLLISION_STATIC("Наезд на препятствие"),
        ROLLOVER("Переворот ТС"),
        PEDESTRIAN("Наезд на пешехода"),
        PASSENGER_FALL("Падение пассажира"),
        FIRE("Возгорание ТС"),
        CARGO_LOSS("Падение груза"),
        FALLEN_TREE("Упавшее дерево"),
        LANDSLIDE("Оползень/обвал"),
        POWER_LINE_DOWN("Обрыв ЛЭП");
    }
}