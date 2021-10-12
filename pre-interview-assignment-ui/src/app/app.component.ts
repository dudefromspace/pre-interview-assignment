import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { combineAll, map } from 'rxjs/operators';
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
  displayedColumns: string[] = ['anum', 'bnum', 'callCategory', 'charge', 'fileName', 'id', 'roundedUsedAmount', 'serviceType', 'startDateTime', 'subscriberType', 'usedAmount'];
  dataSource: any;

  fileName = '';
  fileUrl: any;

  constructor(private http: HttpClient, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {

  }

  ngAfterViewInit() {
    this.getCdr().subscribe(element => {
      this.cdrs = element;
      this.dataSource = new MatTableDataSource<any>(this.cdrs);
      this.dataSource.paginator = this.paginator;
    })
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


  inputEvent(event: any) {
    // Return date object
    const m: Moment = event.value;
    console.log(m.format('YYYYMMDD'));
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


}