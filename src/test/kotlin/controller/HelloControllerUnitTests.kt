package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.ui.Model
import org.springframework.ui.ExtendedModelMap

class HelloControllerUnitTests {
    private lateinit var controller: HelloController
    private lateinit var model: Model
    
    @BeforeEach
    fun setup() {
        controller = HelloController("Test Message")
        model = ExtendedModelMap()
    }
    
    @Test // Modified test for default greeting
    fun `should return welcome view with default message`() {
        val view = controller.welcome(model, "")
        
        assertThat(view).isEqualTo("welcome")
        // Get the message attribute and check its contents
        val message = model.getAttribute("message") as String
        // Check that the message starts with "Good" and contains "Test Message"
        assertThat(message).startsWith("Good")
        assertThat(message).contains("Test Message")
        // Check that name attribute is empty
        assertThat(model.getAttribute("name")).isEqualTo("")
    }
    
    @Test // Modified test for personalized greeting
    fun `should return welcome view with personalized message`() {
        // Get the view name when a name is provided
        val view = controller.welcome(model, "Developer")

        assertThat(view).isEqualTo("welcome")
        // Get the message attribute and check its contents
        val message = model.getAttribute("message") as String
        // Check that the message starts with "Good" and contains "Developer"
        assertThat(message).startsWith("Good")
        assertThat(message).contains("Developer")
        assertThat(model.getAttribute("name")).isEqualTo("Developer")
    }
    
    @Test
    fun `should return API response with timestamp`() {
        val apiController = HelloApiController()
        val response = apiController.helloApi("Test")
        
        // Check that the response contains the expected keys and values
        assertThat(response).containsKey("message")
        assertThat(response).containsKey("timestamp")
        assertThat(response["message"]).startsWith("Good")
        assertThat(response["message"]).contains("Test")
        assertThat(response["timestamp"]).isNotNull()
    }
}
