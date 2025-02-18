# UYARI

* Servisleri çalıştırdığınız zaman hata alıyorsanız jwt token süresi geçmiştir. Yeni bir jwt oluşturup TestData sınıfına
  ekleyiniz

# Class Name : UserController

### Method Name : getAllUsers()

```text
📌Feature: Tüm Kullanıcıları Getir  
   Bir yönetici olarak  
   Tüm kullanıcıları getirmek istiyorum  
   Böylece kullanıcı hesaplarını yönetebilirim  

   ✅Scenario: Kullanıcılar mevcut olduğunda başarılı bir şekilde tüm kullanıcıları getir  
      🟢 Given Sistemde kullanıcılar mevcut  
      🔵 When "/api/v1/users/all" adresine GET isteği gönderdiğimde  
      🟠 Then Yanıt durumu 200 olmalı  
      🟠 And Yanıt JSON formatında olmalı  
      🟠 And Yanıt tüm kullanıcıları içermeli  
      🟠 And İlk kullanıcının kullanıcı adı "rasitesdmr" olmalı  
      🟠 And İkinci kullanıcının kullanıcı adı "rasitesdmr" olmalı  
```

### Method Name : getUserByToken()

```text
📌Feature: Kullanıcıyı Token ile Getirme
   Bir API tüketicisi olarak  
   Token kullanarak kullanıcı bilgilerini almak istiyorum  
   Böylece kullanıcı kimliğini doğrulayabilirim.

   ✅Scenario: Kullanıcı mevcut değil 
      🟢 Given Sistemde sağlanan token ile eşleşen bir kullanıcı yok  
      🔵 When "/api/v1/users" uç noktasına "GET" isteği gönderdiğimde  
      🟠 Then Yanıt durumu 404 olmamalı  
      🟠 And Yanıt içeriğinde "User with the ID <UUID> not found." yazmalı
```

### Method Name : getUserByToken()

```text
📌Feature: Kullanıcıyı Token ile Getirme
   Bir API tüketicisi olarak  
   Token kullanarak kullanıcı bilgilerini almak istiyorum  
   Böylece kullanıcı kimliğini doğrulayabilirim.

   ✅Scenario: Kullanıcı mevcut olduğunda başarılı bir şekilde kullanıcı bilgilerini getir
      🟢 Given Sistemde sağlanan token ile eşleşen bir kullanıcı mevcut
      🔵 When "/api/v1/users" uç noktasına "GET" isteği gönderdiğimde
      🟠 Then Yanıt durumu 200 olmalı 
      🟠 And Yanıt JSON formatında olmalı
      🟠 And Yanıt içeriğinde kullanıcının adı "Raşit" olmalı
      🟠 And Yanıt içeriğinde kullanıcının soyadı "Eşdemir" olmalı
```

# Class Name : UserService

### Method Name : givenDatabaseErrorOccurs_whenSavingUser_thenShouldThrowInternalServerErrorException()

```text
📌Feature: Kullanıcı Kaydetme
   Bir API tüketicisi olarak  
   Yeni bir kullanıcı kaydetmek istiyorum
   Böylece sisteme giriş yapabilirim.

   ✅Scenario: Kullanıcı kaydetme işlemi başarısız olduğunda hata fırlatılmalı
      🟢 Given Kullanıcıyı kaydederken veritabanı hatası oluşuyor
      🔵 When Kullanıcı kaydetme işlemi gerçekleştirildiğinde 
      🟠 Then Internal Server Error hatası fırlatılmalı
```

### Method Name : givenValidUserData_whenSavingUser_thenShouldSaveSuccessfully()

```text
📌Feature: Kullanıcı Kaydetme
   Bir API tüketicisi olarak  
   Yeni bir kullanıcı kaydetmek istiyorum
   Böylece sisteme giriş yapabilirim.

   ✅Scenario: Kullanıcı başarıyla kaydedilmeli
      🟢 Given Kullanıcı kaydetme işlemi başarılı olacak şekilde yapılandırılmış
      🔵 When Kullanıcı kaydetme işlemi gerçekleştirildiğinde
      🟠 Then Kullanıcı başarıyla kaydedilmeli
```

### Method Name : givenUserExists_whenCreatingUser_thenShouldThrowAlreadyAvailableException()

```text
📌Feature: Kullanıcı Oluşturma
   Bir API tüketicisi olarak
   Yeni bir kullanıcı oluşturmak istiyorum
   Böylece sisteme giriş yapabilirim.
   
   ✅Scenario: Kullanıcı zaten mevcutsa hata fırlatılmalı
      🟢 Given Sistemde aynı kullanıcı adına sahip bir kullanıcı zaten mevcut
      🔵 When Yeni kullanıcı oluşturma işlemi gerçekleştirildiğinde
      🟠 Then AlreadyAvailableException hatası fırlatılmalı
      🟠 And Hata mesajı "User with the username <username> already available." olmalı
```

### Method Name : givenRoleDoesNotExist_whenCreatingUser_thenShouldThrowNotAvailableException()

```text
📌Feature: Kullanıcı Oluşturma
   Bir API tüketicisi olarak
   Yeni bir kullanıcı oluşturmak istiyorum
   Böylece sisteme giriş yapabilirim.
   
   ✅Scenario: Kullanıcı rolü mevcut değilse hata fırlatılmalı
      🟢 Given Kullanıcı adı sistemde mevcut değil
      🟢 And Belirtilen rol sistemde mevcut değil
      🔵 When Yeni kullanıcı oluşturma işlemi gerçekleştirildiğinde
      🟠 Then NotAvailableException hatası fırlatılmalı
      🟠 And Hata mesajı "Role with the role name <role_name> not found." olmalı
```

### Method Name : givenSavingUserFails_whenCreatingUser_thenShouldThrowInternalServerErrorException()

```text
📌Feature: Kullanıcı Oluşturma
   Bir API tüketicisi olarak
   Yeni bir kullanıcı oluşturmak istiyorum
   Böylece sisteme giriş yapabilirim.
   
   ✅Scenario:  Kullanıcı kaydedilemezse iç sunucu hatası fırlatılmalı
      🟢 Given Kullanıcı adı sistemde mevcut değil
      🟢 And Belirtilen rol sistemde mevcut
      🟢 And Kullanıcı kaydedilirken veritabanı hatası oluşur
      🔵 When Yeni kullanıcı oluşturma işlemi gerçekleştirildiğinde
      🟠 Then  InternalServerErrorException hatası fırlatılmalı
      🟠 And Hata mesajı "User Not Saved : Database Error" olmalı
```

### Method Name : givenUserDoesNotExistAndRoleExists_whenCreatingUser_thenShouldCreateUserSuccessfully()

```text
📌Feature: Kullanıcı Oluşturma
   Bir API tüketicisi olarak
   Yeni bir kullanıcı oluşturmak istiyorum
   Böylece sisteme giriş yapabilirim.
   
   ✅Scenario:  Kullanıcı başarıyla oluşturulmalı
      🟢 Given Kullanıcı adı sistemde mevcut değil
      🟢 And Belirtilen rol sistemde mevcut
      🔵 When Yeni kullanıcı oluşturma işlemi gerçekleştirildiğinde
      🟠 Then  Kullanıcı başarıyla kaydedilmeli
```

### Method Name : givenUsernameDoesExist_whenRetrievingUserByUsername_thenShouldThrowNotAvailableException()

```text
📌Feature: Kullanıcıyı Kullanıcı Adıyla Getirme
   Bir API tüketicisi olarak
   Belirli bir kullanıcıyı kullanıcı adıyla getirmek istiyorum
   Böylece kullanıcı hesaplarını yönetebilirim.
   
   ✅Scenario:  Kullanıcı adı sistemde mevcut değil
      🟢 Given Belirtilen kullanıcı adı sistemde mevcut değil
      🔵 When Kullanıcı adı ile kullanıcı getirme işlemi gerçekleştirildiğinde
      🟠 Then  Yanıt durumu 404 olmalı
      🟠 And  Yanıt içeriğinde "User with the username <USERNAME> not found." yazmalı
```

### Method Name : givenUsernameExist_whenRetrievingUserByUsername_thenShouldReturnUser()

```text
📌Feature: Kullanıcıyı Kullanıcı Adıyla Getirme
   Bir API tüketicisi olarak
   Belirli bir kullanıcıyı kullanıcı adıyla getirmek istiyorum
   Böylece kullanıcı hesaplarını yönetebilirim.
   
   ✅Scenario:  Kullanıcı adı sistemde mevcut
      🟢 Given Belirtilen kullanıcı adı sistemde mevcut
      🔵 When Kullanıcı adı ile kullanıcı getirme işlemi gerçekleştirildiğinde
      🟠 Then  Yanıt olarak ilgili kullanıcı dönmeli
      🟠 And  Kullanıcı bilgileri doğru olmalı
```

### Method Name : givenUsernameDoesExist_whenCheckingExistence_thenShouldReturnFalse()

```text
📌Feature: Kullanıcı Adının Var Olup Olmadığını Kontrol Etme
   Bir API tüketicisi olarak
   Belirli bir kullanıcı adının sistemde kayıtlı olup olmadığını kontrol etmek istiyorum
   Böylece yeni bir hesap oluştururken doğrulama yapabilirim.
   
   ✅Scenario:  Kullanıcı adı sistemde mevcut değil
      🟢 Given Belirtilen kullanıcı adı sistemde kayıtlı değil
      🔵 When Kullanıcı adı var mı kontrol işlemi gerçekleştirildiğinde
      🟠 Then  Yanıt olarak false dönmeli
```

### Method Name : givenUsernameExist_whenCheckingExistence_thenShouldReturnTrue()

```text
📌Feature: Kullanıcı Adının Var Olup Olmadığını Kontrol Etme
   Bir API tüketicisi olarak
   Belirli bir kullanıcı adının sistemde kayıtlı olup olmadığını kontrol etmek istiyorum
   Böylece yeni bir hesap oluştururken doğrulama yapabilirim.
   
   ✅Scenario: Kullanıcı adı sistemde mevcut
      🟢 Given Belirtilen kullanıcı adı sistemde kayıtlı
      🔵 When Kullanıcı adı var mı kontrol işlemi gerçekleştirildiğinde
      🟠 Then  Yanıt olarak true dönmeli
```

### Method Name : givenUserExist_whenFetchingAllUsers_thenShouldReturnUserList()

```text
📌Feature: Tüm Kullanıcıları Listeleme
   Bir API tüketicisi olarak
   Sistemde kayıtlı tüm kullanıcıları listelemek istiyorum
   Böylece kullanıcıları görüntüleyebilir ve yönetebilirim.
   
   ✅Scenario: Kullanıcılar mevcut
      🟢 Given Sistemde kayıtlı kullanıcılar var
      🔵 When Tüm kullanıcıları listeleme işlemi gerçekleştirildiğinde
      🟠 Then  Kullanıcıların bulunduğu bir liste dönmeli
```

### Method Name : givenUserIdIsNull_whenFetchingUserByToken_thenShouldReturnNull()

```text
📌Feature: Token ile Kullanıcı Bilgisi Alma
   Bir API tüketicisi olarak
   Geçerli bir token ile kullanıcı bilgimi almak istiyorum
   Böylece sisteme giriş yaptığım hesabı sorgulayabilirim.
   
   ✅Scenario: Kullanıcı kimliği yok
      🟢 Given Kullanıcı kimliği null olarak döndürülüyor
      🔵 When Kullanıcı token ile sorgulandığında
      🟠 Then  Sonuç olarak null dönmeli
```

### Method Name : givenUserDoesNotExist_whenFetchingUserByToken_thenShouldThrowNotAvailableException()

```text
📌Feature: Token ile Kullanıcı Bilgisi Alma
   Bir API tüketicisi olarak
   Geçerli bir token ile kullanıcı bilgimi almak istiyorum
   Böylece sisteme giriş yaptığım hesabı sorgulayabilirim.
   
   ✅Scenario: Kullanıcı bulunamadığında hata fırlatılmalı
      🟢 Given Token'dan elde edilen kullanıcı kimliği userId
      🔵 When Kullanıcı bulunamazsa
      🟠 Then  NotAvailableException hatası fırlatılmalı
```

### Method Name : givenUserExist_whenFetchingUserByToken_thenShouldReturnUser()

```text
📌Feature: Token ile Kullanıcı Bilgisi Alma
   Bir API tüketicisi olarak
   Geçerli bir token ile kullanıcı bilgimi almak istiyorum
   Böylece sisteme giriş yaptığım hesabı sorgulayabilirim.
   
   ✅Scenario: Kullanıcı bulunduğunda bilgileri dönmeli
      🟢 Given Token'dan elde edilen kullanıcı kimliği userId
      🔵 When Kullanıcı mevcutsa
      🟠 Then Kullanıcı nesnesi döndürülmeli
```

# Class Name : AuthController

### Method Name : givenInvalidRequest_whenRegistering_thenShouldReturn400BadRequest()

```text
📌Feature: Geçersiz Kayıt Talebi
   Bir kullanıcı olarak
   Hatalı veya eksik bilgilerle kayıt olmaya çalışırsam
   Uygun bir hata mesajı almak istiyorum

   ✅Scenario: Eksik bilgilerle kayıt olma girişimi 400 hatası döndürmeli
      🟢 Given Geçersiz bir kayıt talebi oluşturuldu
      🔵 When Kullanıcı kayıt olmayı dener
      🟠 Then Sunucu 400 BAD REQUEST yanıtı döndürmel
```

### Method Name : givenExistingUsername_whenRegistering_thenShouldReturn409Conflict()

```text
📌Feature: Kayıt İşlemi
   Bir kullanıcı olarak
   Eğer zaten mevcut bir kullanıcı adıyla kayıt olmaya çalışırsam
   Beni bilgilendiren bir hata mesajı almak istiyorum

   ✅Scenario: Var olan bir kullanıcı adıyla kayıt olma girişimi 409 hatası döndürmeli
      🟢 Given Kayıt için kullanılan kullanıcı adı zaten mevcut
      🔵 When Kullanıcı kayıt olmaya çalışır
      🟠 Then Sunucu 409 CONFLICT hatası döndürmeli
```

### Method Name : givenUnavailableRoleName_whenRegistering_thenShouldReturn404NotFound()

```text
📌Feature: Kayıt İşlemi
   Bir kullanıcı olarak
   Eğer sistemde tanımlı olmayan bir rol ile kayıt olmaya çalışırsam
   Beni bilgilendiren bir hata mesajı almak istiyorum

   ✅Scenario: Geçersiz rol adı ile kayıt olma girişimi 404 hatası döndürmeli
      🟢 Given Kayıt sırasında belirtilen rol adı sistemde bulunmuyor
      🔵 When Kullanıcı kayıt olmaya çalışır
      🟠 Then Sunucu 404 NOT FOUND hatası döndürmeli
```

### Method Name : givenUserSaveFailure_whenRegistering_thenShouldReturn500InternalServerError()

```text
📌Feature: Kayıt İşlemi
   Bir kullanıcı olarak
   Eğer kayıt işlemi sırasında bir hata oluşursa
   Sunucunun beni bilgilendiren bir hata mesajı döndürmesini istiyorum

   ✅Scenario: Kullanıcı kaydedilemezse 500 Internal Server Error hatası döndürmeli
      🟢 Given Kullanıcı veritabanına kaydedilemiyor
      🔵 When Kullanıcı kayıt olmaya çalışır
      🟠 Then Sunucu 500 INTERNAL SERVER ERROR hatası döndürmeli
```

### Method Name : givenValidUserRegistration_whenRegistering_thenShouldReturn201Created()

```text
📌Feature: Geçerli kullanıcı kaydı yapıldığında 201 Created döndürmeli
   Bir kullanıcı olarak
   Eğer kayıt işlemi başarılı olursa
   Sunucunun beni bilgilendiren bir başarı mesajı döndürmesini istiyorum

   ✅Scenario: Geçerli kullanıcı kaydı yapıldığında 201 Created döndürmeli
      🟢 Given Kullanıcı geçerli bir kayıt talebinde bulunuyor
      🔵 When Kullanıcı kayıt olmaya çalışır
      🟠 Then Sunucu 201 CREATED durum kodu ve başarı mesajı döndürmeli
```

### Method Name : givenNonExistingUsername_whenLoggingIn_thenShouldReturn404NotFound()

```text
📌Feature: Kullanıcı Girişi
   Bir kullanıcı olarak
   Eğer girdiğim kullanıcı adı sistemde yoksa
   Sunucunun beni bilgilendiren bir hata mesajı döndürmesini istiyorum

   ✅Scenario: Kullanıcı adı sistemde yoksa 404 Not Found döndürmeli
      🟢 Given Kullanıcı geçerli bir giriş talebinde bulunuyor
      🔵 When Kullanıcı adı sistemde yoksa
      🟠 Then Sunucu 404 NOT FOUND durum kodu ve hata mesajı döndürmeli
```

### Method Name : givenInvalidPassword_whenLoggingIn_thenShouldReturn406NotAcceptable()

```text
📌Feature: Kullanıcı Girişi
   Bir kullanıcı olarak
   Eğer girdiğim şifre hatalıysa
   Sunucunun beni bilgilendiren bir hata mesajı döndürmesini istiyorum

   ✅Scenario: Şifre hatalıysa 406 Not Acceptable döndürmeli
      🟢 Given Kullanıcı geçerli bir giriş talebinde bulunuyor
      🔵 When Kullanıcının girdiği şifre hatalıysa
      🟠 Then Sunucu 406 NOT ACCEPTABLE durum kodu ve hata mesajı döndürmeli
```

### Method Name : givenValidCredentials_whenLoggingIn_thenShouldReturn201Created()

```text
📌Feature: Kullanıcı Girişi
   Bir kullanıcı olarak
   Eğer giriş bilgilerim doğruysa
   Başarıyla giriş yapıp bir JWT token almak istiyorum

   ✅Scenario: Geçerli giriş bilgileri ile giriş yapıldığında 201 Created döndürmeli
      🟢 Given Kullanıcı geçerli giriş bilgileri ile giriş yapıyor
      🔵 When Kullanıcının kimlik bilgileri doğrulanıyorsa
      🟠 Then Sunucu 201 CREATED durum kodu ve bir JWT token döndürmeli
```

# Class Name : AuthService

### Method Name : givenExistingUsername_whenCreatingUser_thenShouldThrowAlreadyAvailableException()

```text
📌Feature: Kullanıcı Kaydı
   Bir kullanıcı olarak
   Eğer kayıt olmaya çalıştığım kullanıcı adı zaten varsa
   Uygun bir hata mesajı almak istiyorum

   ✅Scenario: Kullanıcı adı zaten mevcutsa AlreadyAvailableException fırlatmalı
      🟢 Given Kullanıcı zaten var olan bir kullanıcı adıyla kayıt olmaya çalışıyor
      🔵 When Kullanıcı servisi yeni kullanıcıyı oluşturmaya çalışıyorsa
      🟠 Then AlreadyAvailableException fırlatılmalı ve uygun hata mesajı döndürülmeli
```

### Method Name : givenInvalidRoleName_whenCreatingUser_thenShouldThrowNotAvailableException()

```text
📌Feature: Kullanıcı Kaydı
   Bir kullanıcı olarak
   Eğer geçersiz bir rol adı ile kayıt olmaya çalışırsam
   Uygun bir hata mesajı almak istiyorum

   ✅Scenario: Geçersiz rol adı ile kullanıcı oluşturulmaya çalışıldığında NotAvailableException fırlatmalı
      🟢 Given Kullanıcı, sistemde bulunmayan bir rol adıyla kayıt olmaya çalışıyor
      🔵 When Kullanıcı servisi yeni kullanıcıyı oluşturmaya çalışıyorsa
      🟠 Then NotAvailableException fırlatılmalı ve uygun hata mesajı döndürülmeli
```

### Method Name : givenDatabaseError_whenRegisteringUser_thenShouldThrowInternalServerErrorException()

```text
📌Feature: Kullanıcı Kaydı
   Bir kullanıcı olarak
   Eğer sistem hata verirse
   Uygun bir hata mesajı almak istiyorum

   ✅Scenario: Kullanıcı oluşturulurken veritabanı hatası yaşandığında InternalServerErrorException fırlatmalı
      🟢 Given Kullanıcı kaydı sırasında bir veritabanı hatası oluşuyor
      🔵 When Kullanıcı servisi yeni kullanıcıyı oluşturmaya çalışıyorsa
      🟠 Then InternalServerErrorException fırlatılmalı ve uygun hata mesajı döndürülmeli
```

### Method Name : givenAllStepsSuccessful_whenRegisteringUser_thenShouldReturnSuccessMessage()

```text
📌Feature: Kullanıcı Kaydı
   Bir kullanıcı olarak
   Eğer kayıt işlemi başarılı olursa
   Başarı mesajı almak istiyorum

   ✅Scenario: Kullanıcı başarılı şekilde kaydolduğunda "User Register Successful" mesajı döndürülmeli
      🟢 Given Kullanıcı kaydı için gerekli tüm adımlar başarıyla tamamlandı
      🔵 When Kullanıcı servisi yeni kullanıcıyı oluşturduğunda
      🟠 Then "User Register Successful" mesajı döndürülmeli
```

### Method Name : givenUserDoesNotExist_whenLoggingIn_thenShouldThrowNotAvailableException()

```text
📌Feature: Kullanıcı Girişi
   Bir kullanıcı olarak
   Eğer kullanıcı adım sistemde kayıtlı değilse
   "User with the username X not found." hata mesajı almak istiyorum

   ✅Scenario: Kullanıcı adı bulunamadığında NotAvailableException fırlatılmalı
      🟢 Given Kullanıcı adı sistemde mevcut değil
      🔵 When Kullanıcı giriş yapmaya çalıştığında
      🟠 Then "User with the username X not found." mesajı ile NotAvailableException fırlatılmalı
```

### Method Name : givenIncorrectPassword_whenLoggingIn_thenShouldThrowInvalidException()

```text
📌Feature: Kullanıcı Girişi
   Bir kullanıcı olarak
   Eğer kullanıcı adı doğru ancak şifre yanlışsa
   "Invalid Password" hata mesajı almak istiyorum

   ✅Scenario: Yanlış şifre girildiğinde InvalidException fırlatılmalı
      🟢 Given Kullanıcı adı doğru fakat şifre yanlış
      🔵 When Kullanıcı giriş yapmaya çalıştığında
      🟠 Then "Invalid Password" mesajı ile InvalidException fırlatılmalı
```

### Method Name : givenValidCredentials_whenLoggingIn_thenShouldReturnJwtToken()

```text
📌Feature: Kullanıcı Girişi
   Bir kullanıcı olarak
   Eğer geçerli kimlik bilgileri sağlanırsa
   Geçerli bir JWT token almak istiyorum

   ✅Scenario: Geçerli kimlik bilgileri ile giriş yapıldığında JWT token döndürülmeli
      🟢 Given Kullanıcı adı ve şifre doğru
      🔵 When Kullanıcı giriş yaparsa
      🟠 Then JWT token döndürülmeli
```

# Class Name : JwtFilter

### Method Name : givenMissingAuthorizationHeader_whenJwtFilterRuns_thenShouldProceedWithFilterChain()

```text
📌Feature: JWT Filtreleme
   Bir kullanıcı olarak
   Eğer authorization header eksikse
   Filtre zincirinin devam etmesini istiyorum

   ✅Scenario: Authorization header eksik olduğunda filtre zincirine devam edilmeli
      🟢 Given Authorization header eksik
      🔵 When JWT filter çalıştırılırsa
      🟠 Then  Filtre zincirine devam edilmeli
```

### Method Name : givenInvalidAuthorizationHeader_whenJwtFilterRuns_thenShouldProceedWithFilterChain()

```text
📌Feature: JWT Filtreleme
   Bir kullanıcı olarak
   Eğer authorization header geçersizse
   Filtre zincirinin devam etmesini istiyorum

   ✅Scenario: Geçersiz authorization header ile filtre zincirine devam edilmeli
      🟢 Given Geçersiz authorization header
      🔵 When JWT filter çalıştırılırsa
      🟠 Then  Filtre zincirine devam edilmeli
```

### Method Name : givenValidToken_whenJwtFilterRuns_thenShouldSetSecurityContext()

```text
📌Feature:  JWT Token Geçerliliği
   Bir kullanıcı olarak
   Eğer token geçerliyse
   Security context'inin ayarlandığını görmek istiyorum

   ✅Scenario: Geçerli bir token ile security context ayarlanmalı
      🟢 Given  Geçerli bir JWT token
      🔵 When JWT filter çalıştırılırsa
      🟠 Then  Security context set edilmeli ve doğrulama yapılmalı
```

### Method Name : givenExpiredToken_whenJwtFilterRuns_thenShouldThrowExpiredJwtException()

```text
📌Feature:  JWT Token Geçerliliği
   Bir kullanıcı olarak
   Eğer token süresi dolmuşsa
   Expired JWT Exception'ın fırlatıldığını görmek istiyorum

   ✅Scenario: Geçmiş bir token ile expired JWT exception fırlatılmalı
      🟢 Given Süresi dolmuş bir JWT token
      🔵 When JWT filter çalıştırılırsa
      🟠 Then  Expired JWT exception fırlatılmalı ve uygun cevap döndürülmeli
```

### Method Name : givenInvalidToken_whenJwtFilterRuns_thenShouldProceedWithFilterChain()

```text
📌Feature:  JWT Token Geçerliliği
   Bir kullanıcı olarak
   Eğer geçersiz bir JWT token sağlanmışsa
   Filter chain'in devam etmesi gerektiğini görmek istiyorum

   ✅Scenario: Geçersiz bir token ile filter chain'in devam etmesi
      🟢 Given  Geçersiz bir JWT token
      🔵 When JWT filter çalıştırılırsa
      🟠 Then  Filter chain devam etmeli ve güvenlik bağlamında kimlik doğrulama yapılmamalı
```

# Class Name : JwtService

### Method Name : givenValidInputs_whenGeneratingToken_thenShouldIncludeUsernameAndRole()

```text
📌Feature: JWT Token Üretimi
   Bir kullanıcı olarak
   Eğer geçerli parametreler sağlanmışsa
   Token'ın içinde kullanıcı adı ve rolün bulunması gerektiğini görmek istiyorum

   ✅Scenario: Geçerli girişlerle token üretildiğinde kullanıcı adı ve rolün bulunması
      🟢 Given Geçerli kullanıcı kimliği, kullanıcı adı ve rol
      🔵 When Token üretildiğinde
      🟠 Then  Token içinde kullanıcı adı ve rol olmalı
```

### Method Name : givenValidClaimsAndSubject_whenCreatingJwtToken_thenShouldIncludeUsernameRoleAndSubject()

```text
📌Feature: JWT Token Oluşumu
   Bir kullanıcı olarak
   Eğer kullanıcı adı, rol ve konu (subject) sağlanmışsa
   Geçerli bir token oluşturulmasını bekliyorum

   ✅Scenario: Geçerli parametrelerle JWT token oluşturulduğunda içerikte kullanıcı adı, rol ve konu (subject) bulunmalı
      🟢 Given Geçerli kullanıcı adı, rol ve konu bilgisi
      🔵 When Token oluşturulduğunda
      🟠 Then Token içinde kullanıcı adı, rol ve konu bilgisi olmalı
```

### Method Name : givenValidToken_whenExtractingUserId_thenShouldReturnCorrectUserId()

```text
📌Feature: JWT Token'dan User ID Çekme
   Bir kullanıcı olarak
   Eğer geçerli bir token sağlanmışsa
   Token'dan kullanıcı ID'sinin doğru şekilde çıkarılmasını bekliyorum

   ✅Scenario: Geçerli token verildiğinde, token'dan kullanıcı ID'sinin doğru şekilde çıkarılması
      🟢 Given Geçerli bir JWT token
      🔵 When Token'dan kullanıcı ID'si çıkarıldığında
      🟠 Then Çıkarılan ID doğru kullanıcı ID'si olmalı
```

### Method Name : givenValidToken_whenExtractingUserName_thenShouldReturnCorrectUserName()

```text
📌Feature: JWT Token'dan Kullanıcı Adı Çekme
   Bir kullanıcı olarak
   Eğer geçerli bir token sağlanmışsa
   Token'dan kullanıcı adının doğru şekilde çıkarılmasını bekliyorum

   ✅Scenario: Geçerli token verildiğinde, token'dan kullanıcı adının doğru şekilde çıkarılması
      🟢 Given Geçerli bir JWT token
      🔵 When Token'dan kullanıcı adı çıkarıldığında
      🟠 Then Çıkarılan kullanıcı adı beklenen kullanıcı adı olmalı
```

### Method Name : givenSecretKeyIsSet_whenRetrievingSignKey_thenShouldReturnNonNullHmacSHAKey()

```text
📌Feature: JWT Gizli Anahtarının (Secret Key) Alınması
   Bir kullanıcı olarak
   Eğer sistemde gizli anahtar (secret key) tanımlanmışsa
   Bu anahtarın boş olmamasını ve doğru algoritmayı içermesini bekliyorum

   ✅Scenario: Gizli anahtar tanımlandığında, geçerli bir HmacSHA algoritmalı anahtar dönmelidir
      🟢 Given Gizli anahtarın sistemde tanımlı olması
      🔵 When getSignKey() metodu çağrıldığında
      🟠 Then Dönüş değeri boş olmamalı ve "HmacSHA" içermelidir
```

### Method Name : givenValidJwtToken_whenExtractingClaims_thenShouldReturnAllClaims()

```text
📌Feature: JWT Token'dan Tüm Hak Taleplerini (Claims) Çıkartma
   Bir kullanıcı olarak
   Eğer geçerli bir JWT tokenim varsa
   Bu token'dan tüm hak taleplerini (claims) doğru şekilde alabilmeyi bekliyorum

   ✅Scenario: Geçerli bir token ile username, role ve subject değerleri döndürülmelidir
      🟢 Given JWT token geçerlidir
      🔵 When extractAllClaims(token) metodu çağrıldığında
      🟠 Then Username, role ve subject değerleri doğru dönmelidir
```

### Method Name : givenValidToken_whenCheckingValidity_thenShouldReturnTrue()

```text
📌Feature: JWT Token Geçerliliğini Kontrol Etme
   Bir kullanıcı olarak
   Eğer JWT tokenim geçerli ve süresi dolmamışsa
   Bu tokenin geçerli olduğunu doğrulamak istiyorum

   ✅Scenario: Token süresi dolmamış ve username eşleşiyorsa, geçerli olmalıdır
      🟢 Given JWT token süresi dolmamıştır ve username eşleşmektedir
      🔵 When isTokenValid(token, userDetails) metodu çağrıldığında
      🟠 Then Metot true dönmelidir
```

### Method Name : givenMismatchedUsername_whenCheckingValidity_thenShouldReturnFalse()

```text
📌Feature: JWT Token Geçerliliğini Kontrol Etme
   Bir kullanıcı olarak
   Eğer JWT tokenimdeki username ile doğrulama yapılan username uyuşmuyorsa
   Bu tokenin geçersiz olarak işaretlenmesini istiyorum

   ✅Scenario: Token username ile userDetails username eşleşmiyorsa, geçersiz olmalıdır
      🟢 Given JWT token username'i ile userDetails içindeki username farklıdır
      🔵 When isTokenValid(token, userDetails) metodu çağrıldığında
      🟠 Then Metot false dönmelidir
```

### Method Name : givenNonExpiredToken_whenCheckingExpiration_thenShouldReturnFalse()

```text
📌Feature: JWT Token Süresinin Dolup Dolmadığını Kontrol Etme
   Bir kullanıcı olarak
   Eğer JWT tokenimin süresi dolmamışsa
   Bu tokenin geçerli olarak işaretlenmesini istiyorum

   ✅Scenario: Token süresi dolmamışsa, geçerli olmalıdır
      🟢 Given JWT token süresi dolmamıştır
      🔵 When isTokenExpired(token) metodu çağrıldığında
      🟠 Then Metot false dönmelidir
```