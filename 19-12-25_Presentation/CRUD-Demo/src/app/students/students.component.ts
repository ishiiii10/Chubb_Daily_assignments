import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Student } from './student.model';
import { StudentService } from '../services/student.service';

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})
export class StudentsComponent implements OnInit {

  students: Student[] = [];

  newStudent: Student = {
    id: 0,
    name: '',
    course: ''
  };

  selectedStudent: Student | null = null;

  constructor(private studentService: StudentService) {}

  ngOnInit(): void {
    this.getStudents();
  }

  // READ
  getStudents(): void {
    this.students = this.studentService.getStudents();
  }

  // CREATE
  addStudent(): void {
    const student = { ...this.newStudent };
    this.studentService.addStudent(student);
    this.resetForm();
  }

  // SELECT FOR UPDATE
  editStudent(student: Student): void {
    this.selectedStudent = { ...student };
  }

  // UPDATE
  updateStudent(): void {
    if (this.selectedStudent) {
      this.studentService.updateStudent(this.selectedStudent);
      this.selectedStudent = null;
    }
  }

  // DELETE
  deleteStudent(id: number): void {
    this.studentService.deleteStudent(id);
  }

  resetForm(): void {
    this.newStudent = { id: 0, name: '', course: '' };
  }
}