from selenium import webdriver
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

            profile = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/a')
            driver.execute_script("arguments[0].click();", profile)

            ddown_menu = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/ul')
            driver.execute_script("arguments[0].click();", ddown_menu)

            product = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/ul/li[1]/a')
            driver.execute_script("arguments[0].click();", product)
            time.sleep(1)

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
            user_field.send_keys('facundo')
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

            ddown_menu = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/a')
            driver.execute_script("arguments[0].click();", ddown_menu)

            product_button = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/ul/li[1]/a')
            driver.execute_script("arguments[0].click();", product_button)
            time.sleep(1)

            edit_button = driver.find_element(By.CLASS_NAME, "bi-pen")
            edit_button.click()
            time.sleep(1)

            cancel_button = driver.find_element(By.CLASS_NAME, "bi-x")
            cancel_button.click()
            time.sleep(1)

            name = driver.find_element(By.ID, "nombre")
            description = driver.find_element(By.ID, "descripcion")
            type_field = driver.find_element(By.NAME, "tipo")
            price = driver.find_element(By.ID, "precio")
            cost = driver.find_element(By.ID, "costo")

            name.click()
            time.sleep(1)
            description.click()
            time.sleep(1)
            type_field.click()
            time.sleep(1)
            price.click()
            time.sleep(1)
            cost.click()
            time.sleep(1)

            ddown_menu = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/a')
            driver.execute_script("arguments[0].click();", ddown_menu)
            time.sleep(1)

            product_type = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/ul/li[2]/a')
            driver.execute_script("arguments[0].click();", product_type)
            time.sleep(1)

            ddown_menu = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/a')
            driver.execute_script("arguments[0].click();", ddown_menu)
            time.sleep(1)

            delivery = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/ul/li[3]/a')
            driver.execute_script("arguments[0].click();", delivery)
            time.sleep(1)

            ddown_menu = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/a')
            driver.execute_script("arguments[0].click();", ddown_menu)
            time.sleep(1)

            sells = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/ul/li[4]/a')
            driver.execute_script("arguments[0].click();", sells)
            time.sleep(1)

            ddown_menu = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/a')
            driver.execute_script("arguments[0].click();", ddown_menu)
            time.sleep(1)

            offers = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/ul/li[5]/a')
            driver.execute_script("arguments[0].click();", offers)
            time.sleep(1)

            ddown_menu = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/a')
            driver.execute_script("arguments[0].click();", ddown_menu)
            time.sleep(1)

            users = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/ul/li[6]/a')
            driver.execute_script("arguments[0].click();", offers)
            time.sleep(1)

            ddown_menu = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/a')
            driver.execute_script("arguments[0].click();", ddown_menu)
            time.sleep(1)

            config = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/ul/li[7]/a')
            driver.execute_script("arguments[0].click();", config)
            time.sleep(1)

            logout_btn = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[4]/a')
            logout_btn.click()
            time.sleep(3)
        finally:
            driver.quit()
