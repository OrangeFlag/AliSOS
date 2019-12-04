import {Component, OnInit} from '@angular/core';
import {PatientsService} from "../patients.service";
import {MatTable} from '@angular/material/table';

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit {
  date = new Date(Date.now());

  patients = [];
  columnsToDisplay = ['anamnesis', 'address', 'phone', 'time', 'doctorType'];

  formatDate(date: Date): Date {
    return new Date(date);
  }

  formatAnamnesis(anamnesis: string): string[] {
    return anamnesis.split("\n");
  }

  getPatients(): void {
    this.patientService.getPatients()
      .subscribe(patients => this.patients = patients);
  }

  constructor(private patientService: PatientsService) {
  }

  ngOnInit() {
    this.getPatients();
  }

}
