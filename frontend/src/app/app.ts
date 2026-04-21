import { Component } from '@angular/core';
import { Home } from './components/home/home';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [Home],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
}