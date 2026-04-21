import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class Api {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  shortenUrl(data: any) {
    return this.http.post(
      this.baseUrl + '/api/url/shorten',
      data
    );
  }

  getSummary() {
    return this.http.get(
      this.baseUrl + '/api/analytics/summary'
    );
  }
}