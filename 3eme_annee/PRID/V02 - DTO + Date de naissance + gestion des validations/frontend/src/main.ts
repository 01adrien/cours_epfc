import {
  provideHttpClient,
  withInterceptorsFromDi,
} from "@angular/common/http";
import { APP_ID, enableProdMode, InjectionToken } from "@angular/core";
import { bootstrapApplication } from "@angular/platform-browser";
import { provideAnimationsAsync } from "@angular/platform-browser/animations/async";
import { provideRouter } from "@angular/router";
import { AppComponent } from "./app/components/app/app.component";
import { CounterComponent } from "./app/components/counter/counter.component";
import { FetchDataComponent } from "./app/components/fetch-data/fetch-data.component";
import { HomeComponent } from "./app/components/home/home.component";
import { environment } from "./environments/environment";
import { MemberListComponent } from "./app/components/member-list/memberList.component";

export const BASE_URL = new InjectionToken<string>("http://127.0.0.1:5000/");

export function getBaseUrl() {
  return document.getElementsByTagName("base")[0].href;
}

const providers = [
  provideHttpClient(withInterceptorsFromDi()),
  provideRouter([
    { path: "", component: HomeComponent, pathMatch: "full" },
    { path: "counter", component: CounterComponent },
    { path: "fetch-data", component: FetchDataComponent },
    { path: "member-list", component: MemberListComponent },
  ]),
  provideAnimationsAsync(),
  { provide: APP_ID, useValue: "prid-tuto" },
  { provide: BASE_URL, useFactory: getBaseUrl, deps: [] },
];

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, { providers }).catch((err) =>
  console.error(err)
);
