import java.util.*;

public class Directory {

    Contact contactData;

    HashMap<String, String> mName_UUID = new HashMap<>();/// keep this as string
    HashMap<String, String> mEmail_UUID = new HashMap<>(); ///  keep this as string
    HashMap<String, Contact> mUUID_Data = new HashMap<>();

    TreeSet<String> mOrdered = new TreeSet<>();


    String mName;
    String mPhone;
    String mEmail;
    String mUuid;
    String mTemp;
    String mTemp1[];
    int count = 1;

    public void add() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the Name:");
        while (true) {
            mName = scan.nextLine();
            mName = mName.substring(0, 1).toUpperCase() + mName.substring(1);
            if (!mName_UUID.containsKey(mName)) {
                break;
            }
            System.out.println("Name altready Exists.. Please enter an alternate name");
        }

        System.out.println("Enter the Phone Number");
        invalid:
        while (true) {
            mPhone = scan.next();
            for (int i = 0; i < mPhone.length(); i++) {
                if (!('0' <= mPhone.charAt(i) && mPhone.charAt(i) <= '9') || mPhone.charAt(i) == '+') {
                    System.out.println("Enter a Valid Phone Number");
                    continue invalid;
                }
                if (i == mPhone.length() - 1)
                    break;
            }
            break;
        }

        System.out.println("Enter the Email Address");
        while (true) {
            mEmail = scan.next();
            mTemp1 = mEmail.split("@");
            if (!(mTemp1.length == 2))
                System.out.println("Invalid Email format..");
            else if (!mEmail_UUID.containsKey(mEmail))
                break;
            else
                System.out.println("Email already exists.. Please enter the new Email address");
        }

        mUuid = UUID.randomUUID().toString();

        mName_UUID.put(mName, mUuid);

        mEmail_UUID.put(mEmail, mUuid);

        mUUID_Data.put(mUuid, new Contact(mName, mPhone, mEmail));

        mOrdered.add(mName);
    }

    public void search() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Search:");
        System.out.println("1.Search by Name");
        System.out.println("2.Search by Email");
        System.out.println("- Press anyother key to return to phonebook -");

        switch (scan.next()) {
            case "1": {
                Scanner scan1 = new Scanner(System.in);
                System.out.println("Enter the name to be searched");
                mName = scan1.nextLine();
                mName = mName.substring(0, 1).toUpperCase() + mName.substring(1);
                if (mName_UUID.containsKey(mName)) {
                    count = 1;
                    show(mName_UUID.get(mName));
                    break;
                }
                System.out.println("Record Not Found.. Try Again..");
                break;
            }

            case "2": {
                Scanner scan1 = new Scanner(System.in);
                System.out.println("Enter the Email to be searched");
                mEmail = scan1.nextLine();
//                mTemp = mTemp.substring(0,1).toUpperCase()+mTemp.substring(1);
                if (mEmail_UUID.containsKey(mEmail)) {
                    count = 1;
                    show(mEmail_UUID.get(mEmail));
                    break;
                }
                System.out.println("Record Not Found.. Try Again..");
                break;
            }
            default:
                System.out.println("Returning to Phone Book");
                return;
        }

    }

    public void remove() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Remove:");
        System.out.println("1.Remove by Name");
        System.out.println("2.Remove by email");
        System.out.println("- Press anyother key to return to phonebook -");

        switch (scan.nextInt()) {

            case 1: {
                Scanner scan1 = new Scanner(System.in);
                System.out.println("Enter the name to remove the record..");
                mName = scan1.nextLine();
                mTemp = mTemp.substring(0, 1).toUpperCase() + mTemp.substring(1);
                if (mName_UUID.containsKey(mTemp)) {
                    contactData = mUUID_Data.get(mName_UUID.get(mName));
                    mName_UUID.remove(mTemp1[0]);
                    mUUID_Data.remove(mName_UUID.get(mTemp));
                    mEmail_UUID.remove(mTemp1[2]);
                    mOrdered.remove(mTemp1[0]);
                    System.out.println("Record Removed...");
                } else {
                    System.out.println("Record not found..");
                }
                break;
            }
            case 2: {
                Scanner scan1 = new Scanner(System.in);
                System.out.println("Enter the email to remove the record..");
                mTemp = scan1.nextLine();
                if (mEmail_UUID.containsKey(mTemp)) {
                    contactData = mUUID_Data.get(mEmail_UUID.get(mTemp));
                    mName_UUID.remove(contactData.getmName());
                    mUUID_Data.remove(mEmail_UUID.get(contactData.getmEmail()));
                    mEmail_UUID.remove(contactData.getmEmail());
                    mOrdered.remove(contactData.getmName());
                    System.out.println("Record Removed...");
                } else {
                    System.out.println("Record not found..");
                }
                break;
            }
        }
    }

    public void showAll() {
        count = 1;
        for (String iterator : mOrdered) {
            show(mName_UUID.get(iterator));
            count++;
        }
    }

    public void edit() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Edit:");
        System.out.println("1.Edit by Name");
        System.out.println("2.Edit by email");
        System.out.println("- Press anyother key to return to phonebook -");

        switch (scan.nextInt()) {
            case 1: {
                Scanner scan1 = new Scanner(System.in);
                System.out.println("Enter the name to fetch the record..");
                mTemp = scan1.nextLine();
                mTemp = mTemp.substring(0, 1).toUpperCase() + mTemp.substring(1);
                if (mName_UUID.containsKey(mTemp)) {
                    contactData = mUUID_Data.get(mName_UUID.get(mTemp));
                    editBy(contactData, mName_UUID.get(mTemp));
                }
                break;
            }
            case 2: {
                Scanner scan1 = new Scanner(System.in);
                System.out.println("Enter the email to fetch the record..");
                mTemp = scan1.nextLine();
                if (mEmail_UUID.containsKey(mTemp)) {
                    contactData = mUUID_Data.get(mEmail_UUID.get(mTemp));
                    editBy(contactData, mEmail_UUID.get(mTemp));
                }
                break;
            }
        }
    }

    public void show(String id) {
        if (count == 1) {
            System.out.print("No.");
            System.out.format("%6s", " ");
            System.out.format("%-20s", "Name");
            System.out.format("%-20s", "Phone Number");
            System.out.format("%-20s", "Email");
            System.out.println();
        }

        contactData = mUUID_Data.get(id);
        System.out.print(count);
        System.out.format("%6s", " ");
        System.out.format("%-20s", contactData.getmName());
        System.out.format("%-20s", contactData.getmPhonenumber());
        System.out.format("%-20s", contactData.getmEmail());
        System.out.println();
    }

    public String validateEmail(String mTemp) {
        while (true) {
            Scanner scan1 = new Scanner(System.in);
            String mTemp2[] = mTemp.split("@");
            if (!(mTemp2.length == 2))
                System.out.println("Invalid Email format..");
            else if (mEmail_UUID.containsKey(mTemp))
                System.out.println("Email already exists..");
            else
                return mTemp;
            System.out.println("Enter a new Email address");
            mTemp = scan1.nextLine();
        }
    }

    public String validateName(String mTemp) {
        while (true) {
            Scanner scan1 = new Scanner(System.in);
            mTemp = mTemp.substring(0, 1).toUpperCase() + mTemp.substring(1);
            if (mTemp == null || mTemp.isEmpty()) {
                System.out.println("No Entries found");
            } else if (mName_UUID.containsKey(mTemp)) {
                System.out.println("Name already Exists..");
            } else
                return mTemp;
            System.out.println("Enter a new name");
            mTemp = scan1.nextLine();
        }
    }

    public String validatePhonenumber(String mTemp) {
        Scanner scan = new Scanner(System.in);
        invalid:
        while (true) {
            for (int i = 0; i < mTemp.length(); i++) {
                if (!('0' <= mTemp.charAt(i) && mTemp.charAt(i) <= '9') || mTemp.charAt(i) == '+') {
                    System.out.println("Enter a Valid Phone Number");
                    mTemp = scan.nextLine().trim();
                    continue invalid;
                }
                if (i == mTemp.length() - 1)
                    return mTemp;
            }
            break;
        }
        return mTemp;
    }


    public void editBy(Contact contactData, String uuid) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Edit:");
        System.out.println("1.Edit Name");
        System.out.println("2.Edit Phone number");
        System.out.println("3.Edit Email");
        System.out.println("- Press anyother key to exit -");

        String newValue;
        switch (scan.nextLine()) {
            case "1": {
                System.out.println("Enter a new name:");
                mTemp = scan.nextLine();
                mTemp = mTemp.substring(0, 1).toUpperCase() + mTemp.substring(1);
                newValue = validateName(mTemp);
                save(1, contactData, newValue, uuid);
                contactData = mUUID_Data.get(uuid);
                System.out.println("Editing other components..");
                editBy(contactData, uuid);
                break;
            }
            case "2": {
                System.out.println("Enter a new phone number");
                mTemp = scan.nextLine();
                newValue = validatePhonenumber(mTemp);
                save(2, contactData, newValue, uuid);
                contactData = mUUID_Data.get(uuid);
                System.out.println("Editing other components..");
                editBy(contactData, uuid);
                break;
            }
            case "3": {
                System.out.println("Enter a new email");
                mTemp = scan.nextLine();
                newValue = validateEmail(mTemp);
                save(3, contactData, newValue, uuid);
                contactData = mUUID_Data.get(uuid);
                System.out.println("Editing other components..");
                editBy(contactData, uuid);
                break;
            }
            default:
                return;
        }
    }


    public void save(int componentToEdit, Contact contactData, String newValue, String uuid) {
        switch (componentToEdit) {
            case 1: {
                mName_UUID.remove(contactData.getmName());
                mName_UUID.put(newValue, uuid);
                mUUID_Data.put(uuid, new Contact(newValue, contactData.getmPhonenumber(), contactData.getmEmail()));
                mOrdered.remove(contactData.getmName());
                mOrdered.add(newValue);
                break;
            }
            case 2: {
                mUUID_Data.put(uuid, new Contact(contactData.getmName(), newValue, contactData.getmEmail()));
                break;
            }
            case 3: {
                mEmail_UUID.remove(contactData.getmEmail());
                mEmail_UUID.put(newValue, uuid);
                mUUID_Data.put(uuid, new Contact(contactData.getmName(), contactData.getmPhonenumber(), newValue));
                break;
            }
        }
    }
}