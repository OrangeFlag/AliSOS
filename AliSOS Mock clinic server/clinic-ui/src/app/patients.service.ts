import {Injectable} from '@angular/core';
import {Patient} from "./patient";
import {PATIENTS} from "./mock-patients";
import {Observable, of} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from "../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class PatientsService {

  private patientsUrl = "/api/patients/";

  getPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(this.patientsUrl);
  }

  constructor(private http: HttpClient) {
  }
}
