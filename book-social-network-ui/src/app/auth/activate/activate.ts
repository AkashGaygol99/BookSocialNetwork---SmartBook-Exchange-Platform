import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-activate',
  imports: [],
  templateUrl: './activate.html',
  styleUrl: './activate.css',
})
export class Activate {
     message: string = '';

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

  ngOnInit() {
    const code = this.route.snapshot.queryParamMap.get('code');

    if (code) {
      this.http.get(`http://localhost:8080/auth/activate?code=${code}`, { responseType: 'text' })
        .subscribe({
          next: (res) => this.message = res,
          error: () => this.message = "Activation failed!"
        });
    } else {
      this.message = "Invalid activation link!";
    }
  }
}
