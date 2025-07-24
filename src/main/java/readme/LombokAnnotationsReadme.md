# Lombok Annotations (हिंदी में)

यहाँ Lombok की मुख्य annotations के बारे में संक्षिप्त जानकारी दी गई है:

---

- **@Getter और @Setter**

  - अपने class के fields के लिए getter और setter methods अपने आप बना देता है।

- **@FieldNameConstants**

  - हर field के लिए एक constant बनाता है, जिससे field का नाम string के रूप में मिल सके।

- **@ToString**

  - class के लिए toString() method generate करता है।

- **@EqualsAndHashCode**

  - equals() और hashCode() methods generate करता है।

- **@AllArgsConstructor, @RequiredArgsConstructor, @NoArgsConstructor**

  - सभी fields के लिए constructor, जरूरी fields के लिए constructor, और default constructor बनाता है।

- **@Log, @Log4j, @Log4j2, @Slf4j, @XSlf4j, @CommonsLog, @JBossLog, @Flogger, @CustomLog**

  - अलग-अलग logging frameworks के लिए logger field generate करता है।

- **@Data**

  - @Getter, @Setter, @ToString, @EqualsAndHashCode, और @RequiredArgsConstructor को combine करता है।

- **@Builder**

  - Builder pattern के अनुसार object बनाने के लिए methods देता है।

- **@SuperBuilder**

  - Builder pattern को inheritance के साथ use करने देता है।

- **@Singular**

  - Builder के साथ collections को आसानी से add करने देता है।

- **@Jacksonized**

  - Lombok के builder को Jackson के साथ compatible बनाता है।

- **@Delegate**

  - किसी field के methods को अपनी class में delegate करता है।

- **@Value**

  - Immutable class बनाता है (fields final और private)।

- **@Accessors**

  - Getter/Setter के naming style को customize करता है।

- **@Tolerate**

  - Lombok के साथ manually methods add करने देता है।

- **@Wither, @With**

  - Immutable objects के लिए withX() methods बनाता है।

- **@SneakyThrows**

  - Checked exceptions को बिना declare किए throw करने देता है।

- **@StandardException**

  - Custom exception class के लिए standard constructors बनाता है।

- **@val, @var, experimental @var**

  - Local variables के लिए type inference देता है (Java 10+ जैसा)।

- **@UtilityClass**
  - Class को utility class (सिर्फ static methods) बना देता है।

---

**Lombok Config System:**

- Lombok को project level पर configure करने के लिए system, जिससे आप annotation का default behavior बदल सकते हैं।

---

यह file quick reference के लिए है। अधिक जानकारी के लिए [Lombok Documentation](https://projectlombok.org/features/all) देखें।
private User dtoToUser(UserDto userDto) {
// User user = new User();

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
return this.modelMapper.map(userDto,User.class);
}

    public UserDto userToDto(User user) {
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
//        return userDto;
return this.modelMapper.map(user,UserDto.class);
}