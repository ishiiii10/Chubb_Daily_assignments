import { Injectable } from '@angular/core';
import { Student } from '../students/student.model';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private students: Student[] = [
    { id: 1, name: 'Anushrey', course: 'B.Tech' },
    { id: 2, name: 'Rahul', course: 'M.Tech' },
    { id: 3, name: 'Sneha', course: 'B.A' }
  ];

  // READ
  getStudents(): Student[] {
    return this.students;
  }

  // CREATE
  addStudent(student: Student): void {
    this.students.push(student);
  }

  // UPDATE
  updateStudent(updatedStudent: Student): void {
    const index = this.students.findIndex(s => s.id === updatedStudent.id);
    if (index !== -1) {
      this.students[index] = updatedStudent;
    }
  }

  // DELETE
  deleteStudent(id: number): void {
    this.students = this.students.filter(s => s.id !== id);
  }
}