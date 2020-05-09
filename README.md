# WhatsMe_InitialPart1

This part1 can be considered as a login part of WhatsMe where I implemented FireBase Mobile Authentication.

Upto this part of the project there are two Activities.
  1. Main Activity -        Two EditText for mobile number and verification code
                            and a Send Button which is used for recieving the verification code and
                            then verifying the recieved code.
  2. Main Page Activity -   Simple basic activity which contains a logOut button.
                            After logging in user jumps to this activity and can logOut wherever he wants.
                            
Functions Used : Following Functions are Used inorder;
  1. With the help of "userIsLoggedIn()" we will check if the user is intially logged in or not.
     If he is logged in then we will directly jump to the next.
     
  2. "InitialiseAllViews()" - initaialises all the views and bind them to the main_activit layout.
  
  3. Then the onClickListener is set on the button 'Send Verification Code' 
       If user has valid verificationId it will call the method "verifyPhoneNumberWithCode()"
       This will happen when the user has received the code
       Else if verificationId is null it will call the method "startPhoneNumberVerification()"
       
  4. mCallbacks - Callback is attached to PhoneAuthProvider.OnVerificationStateChangedCallbacks
       Three methods are implemented here - 
       1. onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
            Here we will call the method "signInWithPhoneAuthCredential(phoneAuthCredential)" as the verification of code is completed.
            
       2. onVerificationFailed(@NonNull FirebaseException e)
            Can we used to provide error or debuggingPurposes.
            
       3. onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
            Will assign the String s to mVerificationId
            And change the text of button to 'Verify Code' as we have received the code.
            And we will wait till the user clicks 'Verify Code' button.
            
  
startPhoneNumberVerification() - uses the PhoneAuthProvider.getInstance().verifyPhoneNumber(..) built in method by getting the text 
                                 mPhoneNumber EditText.
                                 
verifyPhoneNumberWithCode() - will make the PhoneAuthCredential object from mVerificationId and by retrieving code from mCode EditText
                              ans then call the method "signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential)".

signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) - Will make the user logIn with the phoneAuthCredental.
                                                                         And will the function userIsLoggedIn.
                                                                         
userIsLoggedIn() - will get the currentUser and if the user is not null will call the MainPageActivity.

MainPageActivity contains only single logOut button which logs the user out and bring back him to 
Main Activity by clearing all top activities
                                 
Problems Encountered - Google FireBase was not sending the verification code to mobile.
                       It took a lot of time for me to discover the error and finally I come to know that I have to provide 
                       SHA certificate fingerprints to firebase which I have to generate from android studio for this project.
                       There are two keys - 
                       1. SHA-1
                       2. SHA-256
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
                    
  
                
