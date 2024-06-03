""" from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from django.test import LiveServerTestCase
import time

class UiTests(LiveServerTestCase):

    def test_login(self):
        driver = webdriver.Chrome()
        driver.maximize_window()

        try:
            driver.get('http://localhost:4200')

            ddown_menu = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/ul')
            driver.execute_script("arguments[0].click();", ddown_menu)

            login_button = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/ul/li[1]/a')
            driver.execute_script("arguments[0].click();", login_button)
            time.sleep(1)

            user_field = driver.find_element(By.ID, "user")
            pw_field = driver.find_element(By.CSS_SELECTOR, "[type=password]")


            user_field.send_keys('facundouser')
            time.sleep(1)
            pw_field.send_keys('123456')
            time.sleep(1)

            button = driver.find_element(By.ID, 'ingresar')
            time.sleep(1)

            wait = WebDriverWait(driver, timeout=2)
            wait.until(lambda d : button.is_enabled())

            button.click()
            time.sleep(2)

            WebDriverWait(driver, 5).until(EC.url_changes('http://localhost:4200/login'))
            time.sleep(2)

            catalogue = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[1]/a')
            driver.execute_script("arguments[0].click();", catalogue)
            time.sleep(2)

            add_cart = driver.find_element(By.CLASS_NAME, 'btn-primary')
            add_cart.click()
            time.sleep(2)

            alert = driver.switch_to.alert
            alert.accept()

            add_cart.click()
            time.sleep(2)

            alert = driver.switch_to.alert
            alert.accept()
            time.sleep(2)

            cart = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[6]/a/img')
            driver.execute_script("arguments[0].click();", cart)
            time.sleep(2)

            stripe = driver.find_element(By.CLASS_NAME, 'stripe')
            time.sleep(1)

            wait = WebDriverWait(driver, timeout=2)
            wait.until(lambda d : stripe.is_enabled())

            stripe.click()
            time.sleep(5)

            email = driver.find_element(By.ID, 'email')
            card = driver.find_element(By.ID, 'cardNumber')
            card_expiry = driver.find_element(By.ID, 'cardExpiry')
            card_cvc = driver.find_element(By.ID, 'cardCvc')
            name = driver.find_element(By.ID, 'billingName')

            wait = WebDriverWait(driver, timeout=2)
            wait.until(lambda d : email.is_enabled())

            email.send_keys('juan.perez@gmail.com')
            time.sleep(1)
            card.send_keys('4000 0003 2000 0021')
            time.sleep(1)
            card_expiry.send_keys('10/25')
            time.sleep(1)
            card_cvc.send_keys('123')
            time.sleep(1)
            name.send_keys('JUAN PEREZ')
            time.sleep(1)

            buy_button = driver.find_element(By.CLASS_NAME, 'SubmitButton')
            buy_button.click()
            time.sleep(5)

            cart = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[4]/a')
            driver.execute_script("arguments[0].click();", cart)
            time.sleep(2)
        finally:
            driver.quit()
 """
