""" from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from django.test import LiveServerTestCase
import time

class UiTests(LiveServerTestCase):

    def test_login(self):
        driver = webdriver.Chrome()

        try:
            driver.get('http://localhost:4200')

            hamb_menu = driver.find_element(By.CLASS_NAME, 'navbar-toggler-icon').click()
            time.sleep(2)

            ddown_menu = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/ul')
            driver.execute_script("arguments[0].click();", ddown_menu)

            login_button = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/ul/li[1]/a')
            driver.execute_script("arguments[0].click();", login_button)
            time.sleep(1)

            hamb_menu = driver.find_element(By.CLASS_NAME, 'navbar-toggler-icon').click()

            user_field = driver.find_element(By.ID, "user")
            pw_field = driver.find_element(By.CSS_SELECTOR, "[type=password]")

            user_field.click()
            time.sleep(1)
            pw_field.click()
            time.sleep(1)
            pw_field.click()
            user_field.send_keys('fa')
            time.sleep(1)
            user_field.clear()
            time.sleep(1)
            user_field.send_keys('facundouser')
            time.sleep(1)
            pw_field.send_keys('')
            time.sleep(1)
            pw_field.send_keys('123')
            time.sleep(1)
            pw_field.clear()
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
        finally:
            driver.quit() """
