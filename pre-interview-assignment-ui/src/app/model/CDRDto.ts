export class CDRDto {
     id: number | undefined;
     BNUM: string | undefined;
     ANUM: string | undefined;
     pserviceType: string | undefined;
     callCategory: string | undefined;
     subscriberType: string | undefined;
     startDateTime: string | undefined;
     usedAmount: string | undefined;
     charge: string | undefined;
     fileName: string | undefined;

     CDRDto(object: any){
      this.id = object.id;
      this.BNUM = object.BNUM;
      this.ANUM = object.ANUM;
      this.pserviceType = object.pserviceType;
      this.callCategory = object.callCategory;
      this.subscriberType = object.subscriberType;
      this.startDateTime = object.startDateTime;
      this.usedAmount = object.usedAmount;
      this.charge = object.charge;
      this.fileName = object.fileName;
     }
}
