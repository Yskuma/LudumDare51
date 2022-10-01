package com.livelyspark.ludumdare51.components;

import com.badlogic.ashley.core.Component;
import com.livelyspark.ludumdare51.enums.EntityFactories;

public class FactoryComponent implements Component {
    public EntityFactories entityFactory;

   public FactoryComponent(EntityFactories entityFactory)
   {
       this.entityFactory = entityFactory;
   }
}
