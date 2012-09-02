package com.wable.http.apiproxy.JSONParser;

public enum ResultCode {
	  SUCCESS,
      EXISTS,
      FAIL,
      PROVIDERERROR,
      NOTEXISTS,
      NOTEXISTSEMAIL,
      ALREADYAUTHORIZED,
      CONTROLLERERROR,
      INVALID,
      INVALIDPARAMETER,
      INVALIDPASSWORD,
      INVALIDMOBILE,
      NEEDTOLOGIN,
      EXPIREDSESSION,
      NOTWABLEIDENTITY,
      ONLYAPP,
      DUPLICATEEMAIL,
      DUPLICATEID,
      BLOCKED,
      
      PASSWORDTOOSHORT,
      PASSWORDNOTMATCH,
      INVALIDEMAIL,
      SPERROR,
      
/////////// FB
      FBAPILIMIT,
      FBOAUTH,
      FBAPI,
      FBDUPLICATEID,
      FBNOTEXISTID,
      FBCONNECT,
      FBEXIST,
      FBOVERWRITE,
      FBINVALIDOAUTHTOKEN,
      FBINVALIDUID,
/////////

////////////BIDDING

      EXISTEDAPPROVEDBIDDING,
      NOTOWNEDBIDDING,

      CANNOTDECIDEBIDDING,
      DECIDEDBIDDING,
      COMPLETEDBIDDING,
      REJECTEDBYPROVIDERBIDDING,
      REJECTEDBYREQUESTERBIDDING,
      OTHERCOMPLETED,
      DELETEDBYPROVIDERBIDDING,
      DELETEDBYREQUESTERBIDDING,
      NOFILE,
      EXISTEDBIDDING,
      APPROVEDBIDDING,
      CANCLEDBIDDING,
      NEEDTOACCEPTEDBYREQUESTER,
      NEEDTOACCEPTEDBYPROVIDER,
/////

/////////////REQUEST

      DECIDEDREQUEST,
      COMPLETEDREQUEST,
      DELETEDREQUEST,
      EXPIREDREQUEST,
      APPROVEDREQUEST,
      CANCLEDREQUEST,
/////////////////
     
      
//////// PROVIDE
      DELETEDPROVIDE,
      CANCLEDPROVIDE,
      COMPLETEDPROVIDE,
////////////////////

      BANKEYWORD,
}
