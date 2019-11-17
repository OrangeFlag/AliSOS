import { Component, OnInit } from '@angular/core';
import { PATIENTS } from "../mock-patients";

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit {

  patients = PATIENTS;

  constructor() { }

  ngOnInit() {
  }

}
