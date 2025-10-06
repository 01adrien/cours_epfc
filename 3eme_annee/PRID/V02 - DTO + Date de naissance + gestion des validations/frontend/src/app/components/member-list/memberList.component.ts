import { Component } from "@angular/core";
import { Member } from "../../models/member";
import { MemberService } from "../../services/member.service";

@Component({
  selector: "app-memberlist",
  templateUrl: "./memberlist.component.html",
})
export class MemberListComponent {
  members: Member[] = [];

  constructor(private memberService: MemberService) {
    memberService.getAll().subscribe((members) => {
      this.members = members;
      console.log(members);
    });
  }
}
