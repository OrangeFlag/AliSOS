import {Injectable} from '@angular/core';
import {Patient} from "./patient";
import {PATIENTS} from "./mock-patients";
import { Observable, of } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class PatientsService {

  getPatients(): Observable<Patient[]> {
    return of(PATIENTS);
  }

  constructor() {
  }
}
