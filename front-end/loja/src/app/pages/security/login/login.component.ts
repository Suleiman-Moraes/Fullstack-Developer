import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../shared/services/authentication.service';
import { Title } from '@angular/platform-browser';
import { User } from '../../shared/models/user.model';
import { first } from 'rxjs/operators';
import toastr from "toastr";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  private returnUrl: string;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private titleService: Title
  ) {
    this.titleService.setTitle("Login");
  }

  ngOnInit(): void {
    this.authenticationService.logout();
    this.loginForm = this.formBuilder.group({
      login: [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  onSubmit() {
    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }
    this.returnUrl = 'pages';
    const user: User = this.loginForm.value as User;

    this.authenticationService.login(user)
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.actionsForError('Usuário e/ou senha inválidos.<br>Tente novamente. Caso tenha esquecido a senha, clique em "<a href="#" title="Recuperar senha">Esqueceu sua senha?</a>" para redefini-la.');
        }
      );
  }

  private actionsForError(error) {
    toastr.error(error, {
      "debug": false,
      "positionClass": "toast-top-right",
      "onclick": null,
      "showDuration": "300",
      "hideDuration": "1000",
      "timeOut": "5000",
      "extendedTimeOut": "1000",
      "showEasing": "swing",
      "hideEasing": "linear",
      "showMethod": "fadeIn",
      "hideMethod": "fadeOut"
    });
  }
}
