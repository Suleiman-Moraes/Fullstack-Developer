let URL_API_SERVER = 'http://localhost:8080/api';

if (window.location.host.indexOf('localhost:') > -1) {
  // URL_API_SERVER = 'http://localhost:8080/api';
}

export const environment = {
  production: false,
  test: false,
  URL_LOJA_SERVICE_API: URL_API_SERVER + '/loja',
  URL_CALCULOFRETE_SERVICE_API: URL_API_SERVER + '/calculofrete',
  URL_AUTH_SERVICE_API: URL_API_SERVER + '/auth'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
