import { Injectable, Inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { BASE_URL } from "src/main";
import { Member } from "../models/member";
import { map } from "rxjs/operators";
import { Observable } from "rxjs";
import { plainToInstance } from "class-transformer";

@Injectable({ providedIn: "root" })
export class MemberService {
  constructor(
    private http: HttpClient,
    @Inject(BASE_URL) private baseUrl: string
  ) {}

  getAll(): Observable<Member[]> {
    console.log(this.baseUrl);

    return this.http
      .get<any[]>(`${this.baseUrl}api/members`)
      .pipe(map((res) => plainToInstance(Member, res)));
  }
}
