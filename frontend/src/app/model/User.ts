import { Reimburesement } from "./Reimbursement";

export class User {

    constructor(public id:number,public username:string,public firstName:string,public lastName:string,
        email:string,public authoredReimbursementList: Reimburesement[], public resolvedReimbursementList: Reimburesement[],public role:string) {
            
    }
}