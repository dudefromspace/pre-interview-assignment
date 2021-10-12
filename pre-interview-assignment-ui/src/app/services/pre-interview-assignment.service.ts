import { Injectable } from "@angular/core";
import {HttpClient} from '@angular/common/http';
import {first, map, tap} from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable()
export class PreInterviewAssignmentService {

    constructor(private http:HttpClient){}

    getTotalCallDurationByDate(date : string){
        return this.http.get('http://localhost:8080/cdr/duration'+'?date=' + date).toPromise()
        .then((res:Response|any)=> res)
        .catch((err:Response|any)=>err)
    }

    getCdr(){
        return this.http.get('http://localhost:8080/cdr').toPromise()
        .then((res:Response|any)=> res)
        .catch((err:Response|any)=>err)
    }
    getAnumWithMaxCharges(): Observable<any> {
        return this.http.get<any>('http://localhost:8080/cdr/anum/maxcharge').pipe(map(res => res));
    }
    
    getAnumWithMaxDuration(): Observable<any> {
        return this.http.get<any>('http://localhost:8080/cdr/anum/maxduration').pipe(map(res => res));
    }

    cdrDownload(): Observable<any> {
        return this.http.get('http://localhost:8080/cdr/download').pipe(map(res => res));
    }

    getTotalVolumeByDate(date : string){
        return this.http.get('http://localhost:8080/cdr/volume'+'?date=' + date).toPromise()
        .then((res:Response|any)=> res)
        .catch((err:Response|any)=>err)
    }

    
    getServiceTypeWithMaxCharge(date : string){
        return this.http.get('http://localhost:8080/cdr/serviceType/maxcharge'+'?date=' + date).toPromise()
        .then((res:Response|any)=> res)
        .catch((err:Response|any)=>err)
    }

    getTotalVoiceCallDurationByCategoryAndDate(date : string){
        return this.http.get('http://localhost:8080/cdr/duration/callcategory'+'?date=' + date).toPromise()
        .then((res:Response|any)=> res)
        .catch((err:Response|any)=>err)
    }
}
