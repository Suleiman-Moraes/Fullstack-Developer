import { Router } from '@angular/router';
import { BaseResourceService } from 'src/app/shared/service/base-resource.service';
import { BaseResourceModel } from 'src/app/shared/models/base-resource.model';
import { Injector } from '@angular/core';
import { MessageService } from 'primeng/api';
import toastr from "toastr";
import { Title } from '@angular/platform-browser';
import { AuthenticationService } from 'src/app/pages/shared/services/authentication.service';
import { UserLogado } from 'src/app/pages/shared/models/user-logado.model';

export abstract class BaseResourceListComponent<T extends BaseResourceModel>{

  messageService: MessageService;
  page: number = 0;
  count: number = 10;
  pages: Array<number>;
  paginas: any = "";
  title: string = '';
  toast;
  router: Router;
  authenticationService: AuthenticationService;
  protected titleService: Title;

  currentUser: UserLogado = new UserLogado;

  //Permissões
  fiscalADMINISTRADOR: boolean = null;
  fiscalFISCALIZACAO: boolean = null;
  expediente: boolean = null;

  constructor(
    private resourceService: BaseResourceService<T>,
    protected injector: Injector,
  ) {
    this.messageService = injector.get(MessageService);
    this.router = this.injector.get(Router);
    this.authenticationService = this.injector.get(AuthenticationService);
    this.toast = toastr;
    this.titleService = injector.get(Title);
  }

  ngOnInit() {
    this.paginas = '';
    if (this.titleService.getTitle() == 'Login') {
      this.titleService.setTitle('Loja');
      location.reload();
    }
    this.findByParams();
    this.posNgOnInit();
  }

  ptBR = {
    firstDayOfWeek: 0,
    dayNames: ["Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"],
    dayNamesShort: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"],
    dayNamesMin: ["Do", "Se", "Te", "Qu", "Qu", "Se", "Sa"],
    monthNames: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
    monthNamesShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
    today: 'Hoje',
    clear: 'Limpar'
  };

  maskCnpjCpfCei = {
    mask: [
      {
        mask: '000.000.000-00'
      },
      {
        mask: '00.000.00000/00'
      },
      {
        mask: '00.000.000/0000-00'
      }
    ]
  };

  maskInter = {
    mask: Number,
    scale: 0,
    thousandsSeparator: '',
    padFractionalZeros: false,
    normalizeZeros: false,
    radix: ','
  };

  maskCnpjOrCpf = {
    mask: [
      {
        mask: '000.000.000-00'
      },
      {
        mask: '00.000.000/0000-00'
      }
    ]
  };

  maskTelefone = {
    mask: [
      {
        mask: '(00)0000-0000'
      },
      {
        mask: '(00)00000-0000'
      }
    ]
  };

  maskPlaca = {
    mask: [
      {
        mask: 'aaa0a00'
      },
      {
        mask: 'aaa0000'
      }
    ]
  };

  maskMoney = {
    mask: Number,
    scale: 2,
    thousandsSeparator: '',
    padFractionalZeros: true,
    normalizeZeros: true,
    radix: '.'
  };

  tipoReincidenciaAutoInfracaos = {
    NAO_REINCIDENTE: 'Não Reincidente',
    ESPECIFICA: 'Específica',
    GENERICA: 'Genérica'
  };

  situacoes = {
    ATIVO: 'Ativo',
    INATIVO: 'Inativo'
  };

  get situacaoOptions(): Array<any> {
    return this.getTypes(this.situacoes);
  }

  get tipoReincidenciaAutoInfracaoOptions(): Array<any> {
    return this.getTypes(this.tipoReincidenciaAutoInfracaos);
  }

  // getSituacaoEnum(tipo: string): string{
  //   return SituacaoEnum[tipo];
  // }

  paginate(event) {
    this.count = event.rows;
    this.page = event.page;
    this.findByParams();
    this.posNgOnInit();
    //event.first = Índice do primeiro registro
    //event.rows = Número de linhas para exibir em nova página
    //event.page = Índice da nova página
    //event.pageCount = Número total de páginas
  }

  // paginateParamsRelatorio(event) {
  //   this.count = event.rows;
  //   this.page = event.page;
  //   this.findByParamsRelatorio();
  // }

  // pesquisar(): void {
  //   this.findByParamsRelatorio();
  // }

  temPermissao(per: string): boolean {
    if (this[per] == null) {
      this[per] = this.verificarPermissao(per);
    }
    return this[per];
  }

  //PRIVATE METHODS
  protected showError(detail: string) {
    this.messageService.add({ severity: 'error', summary: 'Erro', detail: detail });
  }

  protected erroServidor(): void {
    this.authenticationService.logout();
    toastr.error("Erro no servidor, tente novamente mais tarde");
    this.router.navigate(["login"]);
  }

  protected tratarResponse(responseApi): void {
    if (responseApi.data == null) {
      responseApi.erros.forEach(x => {
        this.showError(x);
      });
    }
    else {
      this.paginas = responseApi.data;
      this.pages = new Array(this.paginas.totalPages);
    }
  }

  protected findByParams(): void {
    this.resourceService.findbyparamssingle(this.page, this.count).subscribe(
      responseApi => {
        this.tratarResponseApi(responseApi);
      }, err => {
        this.tratarErro(err);
      }
    );
  }

  protected tratarResponseApi(responseApi): void {
    if (responseApi.data == null) {
      responseApi.erros.forEach(x => {
        this.showError(x);
      });
    }
    else {
      this.paginas = responseApi.data;
      this.pages = new Array(this.paginas.totalPages);
    }
  }

  protected tratarErro(err): void {
    if (err.status != 400) {
      this.erroServidor();
    }
    else {
      err.error.erros.forEach(x => {
        this.showError(x);
      });
    }
  }

  protected verificarPermissao(pers: string): boolean {
    if (pers == null || pers.length <= 0) {
      return true;
    }
    // for (let i = 0; i < this.currentUser.roles.length; i++) {
    //   if (pers.toLowerCase() == this.currentUser.roles[i].toLowerCase()) {
    //     return true;
    //   }
    // }
    return false;
  }

  protected getTypes(type: any): any {
    return Object.entries(type).map(
      ([value, text]) => {
        return {
          text: text,
          value: value
        }
      }
    );
  }

  protected download(filename, text) {
    var element = document.createElement('a');
    element.setAttribute('href', text);
    element.setAttribute('download', filename);

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
  }

  //OPCIONAIS
  protected posNgOnInit(): void { }
}
