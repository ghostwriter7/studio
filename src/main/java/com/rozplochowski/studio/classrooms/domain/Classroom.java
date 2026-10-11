package com.rozplochowski.studio.classrooms.domain;

import com.rozplochowski.studio.classrooms.domain.exception.InvalidClassroomException;
import lombok.Getter;

@Getter
public class Classroom {

   private Long id;
   private String name;
   private int capacity;

   public Classroom(String name, int capacity) {
      validateName(name);
      validateCapacity(capacity);

      this.name = name;
      this.capacity = capacity;
   }

   public void updateDetails(String name, int capacity) {
      validateName(name);
      validateCapacity(capacity);

      this.name = name;
      this.capacity = capacity;
   }

   public static Classroom reconstitute(long id, String name, int capacity) {
      var classroom = new Classroom(name, capacity);
      classroom.id = id;
      return classroom;
   }

   private void validateName(String name) {
      if (name == null || name.isBlank()) {
         throw new InvalidClassroomException("Classroom name cannot be null or blank");
      }
   }

   private void validateCapacity(int capacity) {
      if (capacity <= 0) {
         throw new InvalidClassroomException("Classroom capacity cannot be less than or equal to zero");
      }
   }

}
