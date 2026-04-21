import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Api } from '../../services/api';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {

  originalUrl = '';
  shortUrl = '';

  totalUrls = 0;
  totalClicks = 0;

  constructor(private api: Api) {
    this.loadSummary();
  }

  shorten() {
    this.api.shortenUrl({
      originalUrl: this.originalUrl
    }).subscribe((res: any) => {
      this.shortUrl = res.shortUrl;
      this.originalUrl = '';
      this.loadSummary();
    });
  }

  loadSummary() {
    this.api.getSummary().subscribe((res: any) => {
      this.totalUrls = res.totalUrls;
      this.totalClicks = res.totalClicks;
    });
  }

  copyLink() {
    navigator.clipboard.writeText(this.shortUrl);
    alert('Copied!');
  }
}