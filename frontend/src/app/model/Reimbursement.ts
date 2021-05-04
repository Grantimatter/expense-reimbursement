import { User } from "./User";

export class Reimburesement {
    constructor(public id:number, public amount:number, public description:string, public submitted:Date,
        public resolved: Date, public status:string, public type:string, public author:User, public resolver:User) {
        
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.submitted = submitted;
        this.resolved = resolved;
        this.status = status;
        this.type = type;
        this.author = author;
        this.resolver = resolver;
    }
}