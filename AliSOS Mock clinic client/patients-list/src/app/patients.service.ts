import {Injectable} from '@angular/core';
import {Patient} from "./patient";
import {PATIENTS} from "./mock-patients";
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';



@Injectable({
  providedIn: 'root'
})
export class PatientsService {
  private apiUrl = 'http://localhost:3000/api/patients/'

  getPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(this.apiUrl);
  }

  constructor( private http: HttpClient) {
  }
}
