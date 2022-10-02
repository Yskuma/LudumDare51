package com.livelyspark.ludumdare51.entityfactories;

import com.badlogic.ashley.core.Entity;
import com.livelyspark.ludumdare51.enums.GameGenres;

public interface IEntityFactory {
    Entity Create(GameGenres gameGenre, float x, float y);
    Entity ConvertGenre(Entity e, GameGenres gameGenre);
}
