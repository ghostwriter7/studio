package com.rozplochowski.studio.classrooms.domain;

import com.rozplochowski.studio.classrooms.domain.exception.InvalidClassroomException;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Classroom {

   @Setter
   private Long id;
   private String name;
   private int capacity;

   public Classroom(String name, int capacity) {
      setName(name);
      setCapacity(capacity);
   }

   public void updateDetails(String name, int capacity) {
      setName(name);
      setCapacity(capacity);
   }

   private void setName(String name) {
      if (name == null || name.isBlank()) {
         throw new InvalidClassroomException("Classroom name cannot be null or blank");
      }

      this.name = name;
   }

   private void setCapacity(int capacity) {
      if (capacity <= 0) {
         throw new InvalidClassroomException("Classroom capacity cannot be less than or equal to zero");
      }

      this.capacity = capacity;
   }

}
