import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { elementAt, map } from 'rxjs/operators';
import { CDRDto } from './model/CDRDto';
import { Observable } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

import { MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import * as fileSaver from 'file-saver';
import * as _moment from 'moment';
import { default as _rollupMoment, Moment } from 'moment';

import { DomSanitizer } from '@angular/platform-browser';
import { FormControl } from '@angular/forms';
import { VoiceCallInfoDto } from './model/VoiceCallInfoDto';

const moment = _rollupMoment || _moment;

export const MY_FORMATS = {
  parse: {
    dateInput: 'LL',
  },
  display: {
    dateInput: 'YYYY-MM-DD',
    monthYearLabel: 'YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'YYYY',
  },
};

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },

    { provide: MAT_DATE_FORMATS, useValue: MY_FORMATS },
  ],
})
export class AppComponent implements OnInit, AfterViewInit {
  title = 'frontend';
  cdrs: CDRDto[] = [];
  /// for mat table
  @ViewChild(MatPaginator) paginator: MatPaginator;
  displayedColumns: string[] = ['anum', 'bnum', 'callCategory', 'charge', 'fileName', 'roundedUsedAmount', 'serviceType', 'startDateTime', 'subscriberType', 'usedAmount'];
  dataSource: any;
  voiceCallInfoDto : any;
  gprsInfoDto :any;
  fileName = '';
  fileUrl: any;
  selectedDate = moment(new Date()).format('YYYYMMDD');;
  date = new FormControl(new Date());

  totalDurationByDate: any;
  totalVolumeByDate:any;
anumWithMaxCharge :any;
anumWithMaxDuration:any;
serviceWithMaxCharge:any;
errorMessage: any;
chargePerHour: any;
  constructor(private http: HttpClient, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.getAnumWithMaxCharges().subscribe(x=>this.anumWithMaxCharge = x);
    this.getAnumWithMaxDuration().subscribe(x => this.anumWithMaxDuration = x)
  }

  ngAfterViewInit() {
    this.getCdr().subscribe(element => {
      this.cdrs = element;
      this.dataSource = new MatTableDataSource<any>(this.cdrs);
      this.dataSource.paginator = this.paginator;
    })
  }

  inputEvent(event: any) {
    const m: Moment = event.value;
    this.selectedDate = m.format('YYYYMMDD');
    this.totalDurationByDate = null
    this.totalVolumeByDate = null
    this.getTotalVoiceCallDurationByDate(this.selectedDate).subscribe( x => {this.totalDurationByDate = x + ' Minutes'},error => this.errorMessage);
    this.getTotalVolumeByDate(this.selectedDate).subscribe( x => {this.totalVolumeByDate = x + ' MB'},error => this.errorMessage)
    this.getServiceTypeWithMaxCharge(this.selectedDate).subscribe( x => {this.serviceWithMaxCharge = x},error => this.errorMessage);
    this.getTotalVoiceCallDurationByCategoryAndDate(this.selectedDate).subscribe(element =>{
      this.voiceCallInfoDto = element;
    },error => this.errorMessage);
    this.getTotalGPRSVolumeByCategoryAndDate(this.selectedDate).subscribe(element=>{
        this.gprsInfoDto = element
    },error => this.errorMessage)

    this.getChargePerHour(this.selectedDate).subscribe(element=>{
      this.chargePerHour = element
    }, error=>this.errorMessage);
  }

  cdrDownload(): Observable<any> {
    return this.http.get('http://localhost:8080/cdr').pipe(map(res => res));
  }

  getCdr(): Observable<CDRDto[]> {
    return this.http.get<CDRDto[]>('http://localhost:8080/cdr/download')
      .pipe(map((res: CDRDto[]) => {
        return res;
      }));
  }

  getAnumWithMaxCharges(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/cdr/anum/maxcharge').pipe(map(res => res));
  }

  getAnumWithMaxDuration(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/cdr/anum/maxduration').pipe(map(res => res));
  }

  getTotalVoiceCallDurationByDate(date:string) {
    const params = new HttpParams().set('date', date);
    return this.http.get<any>('http://localhost:8080/cdr/duration', { params }).pipe(map(res => res));
  }

  getTotalVolumeByDate(date:string) {
    const params = new HttpParams().set('date', date);
    return this.http.get<any>('http://localhost:8080/cdr/volume', { params }).pipe(map(res => res));
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.fileName = file.name;
      const formData = new FormData();
      formData.append('file', file);
      const upload$ = this.http.post("http://localhost:8080/cdr/upload", formData);
      upload$.subscribe();
    }
  }

  fileDownLoad() {
    let headers = new HttpHeaders();
    headers = headers.append('Accept', 'text/csv; charset=utf-8');
    const download$ = this.http.get("http://localhost:8080/cdr/download", {
      headers: headers,
      observe: 'response',
      responseType: 'text'
    });
    download$.subscribe(response => {
      this.saveFile(response.body, "test.txt");
    });
  }

  saveFile(data: any, filename: any) {
    const blob = new Blob([data], { type: 'text/csv; charset=utf-8' });
    fileSaver.saveAs(blob, filename);
  }

  getChargePerHour(date:string) {
    const params = new HttpParams().set('date', date);
    return this.http.get<any>('http://localhost:8080/cdr/chargePerHour', { params }).pipe(map(res => res));
  }

  
  getServiceTypeWithMaxCharge(date:string) {
    const params = new HttpParams().set('date', date);
    return this.http.get<any>('http://localhost:8080/cdr/serviceType/maxcharge', { params }).pipe(map(res => res));
  }

  getTotalVoiceCallDurationByCategoryAndDate(date:string){
    const params = new HttpParams().set('date', date);
    return this.http.get<any>('http://localhost:8080/cdr/duration/callcategory', { params }).pipe(map(res => res));
  }

  getTotalGPRSVolumeByCategoryAndDate(date:string){
    const params = new HttpParams().set('date', date);
    return this.http.get<any>('http://localhost:8080/cdr/volume/subscriberType', { params }).pipe(map(res => res));
  }
}