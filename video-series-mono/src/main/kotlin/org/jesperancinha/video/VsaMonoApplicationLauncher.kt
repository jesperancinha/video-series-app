package org.jesperancinha.video

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration::class, DataSourceAutoConfiguration::class])
class VsaMonoApplicationLauncher {
   companion object{
       @JvmStatic
       fun main(args: Array<String>) {
           SpringApplication.run(VsaMonoApplicationLauncher::class.java, *args)
       }
   }
}