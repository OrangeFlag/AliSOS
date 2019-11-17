import {Component, OnInit} from '@angular/core';
import {PatientsService} from "../patients.service";

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit {

  patients = [];

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
