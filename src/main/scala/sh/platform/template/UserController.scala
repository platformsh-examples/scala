package sh.platform.template

import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, PostMapping}

@Controller class UserController @Autowired()(private val userRepository: UserRepository){

  @GetMapping(Array("/")) def start(model: Model): String = {
    model.addAttribute("users", userRepository.findAll)
    "index"
  }

  @GetMapping(Array("/signup")) def showSignUpForm(user: User) = "add-user"

  @PostMapping(Array("/adduser")) def addUser(@Valid user: User, result: BindingResult, model: Model): String = {
    if (result.hasErrors) return "add-user"
    userRepository.save(user)
    model.addAttribute("users", userRepository.findAll)
    "index"
  }

  @GetMapping(Array("/edit/{id}")) def showUpdateForm(@PathVariable("id") id: String, model: Model): String = {
    val user = userRepository.findById(id).orElseThrow(() => new IllegalArgumentException("Invalid user Id:" + id))
    model.addAttribute("user", user)
    "update-user"
  }

  @PostMapping(Array("/update/{id}")) def updateUser(@PathVariable("id") id: String, @Valid user: User, result: BindingResult, model: Model): String = {
    if (result.hasErrors) {
      user.setId(id)
      return "update-user"
    }
    userRepository.save(user)
    model.addAttribute("users", userRepository.findAll)
    "index"
  }

  @GetMapping(Array("/delete/{id}")) def deleteUser(@PathVariable("id") id: String, model: Model): String = {
    val user = userRepository.findById(id).orElseThrow(() => new IllegalArgumentException("Invalid user Id:" + id))
    userRepository.delete(user)
    model.addAttribute("users", userRepository.findAll)
    "index"
  }
}