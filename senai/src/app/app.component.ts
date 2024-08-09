import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  constructor(){
  }
  ngOnInit(): void {
      
  }
  title = 'senai';

  
  soma(a: String, b: String){
    let numA = Number(a);
    let numB = Number(b);
    return numA + numB;
  }
}
