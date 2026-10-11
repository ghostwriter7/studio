package com.rozplochowski.studio.classrooms.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Classroom {

   private Long id;
   private String name;
   private byte capacity;

   public Classroom(String name, byte capacity) {
      if (name == null || name.isBlank()) {
         throw new IllegalArgumentException("Classroom name cannot be null or blank");
      }

      if (capacity <= 0) {
         throw new IllegalArgumentException("Classroom capacity cannot be less than or equal to zero");
      }

      this.name = name;
      this.capacity = capacity;
   }

}
